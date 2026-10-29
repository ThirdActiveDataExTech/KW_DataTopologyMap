package kware.common.validator;

import cetus.annotation.DisplayName;
import org.hibernate.validator.internal.engine.MessageInterpolatorContext;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.validation.MessageInterpolator;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Locale;

/**
 * 검증 메시지의 {0} 을 필드의 @DisplayName 값으로 바꾼다.
 * 경로가 목록 요소(categories[0].sortNo)나 중첩 객체(category.categoryNm)여도 필드를 찾고,
 * 찾지 못하거나 @DisplayName 이 없으면 예외 대신 필드명을 쓴다(검증 오류 응답이 예외로 바뀌지 않게).
 */
public class ValidMessageInterpolator extends ParameterMessageInterpolator implements MessageInterpolator {

    @Override
    public String interpolate(String messageTemplate, Context context) {
        String interpolatedMessage = super.interpolate(messageTemplate, context);
        if (interpolatedMessage != null && interpolatedMessage.contains("{0}")) {
            interpolatedMessage = interpolatedMessage.replace("{0}", displayName(context));
        }
        return interpolatedMessage;
    }

    @Override
    public String interpolate(String messageTemplate, Context context, Locale locale) {
        return this.interpolate(messageTemplate, context);
    }

    /** 경로 마지막 필드의 @DisplayName 값. 없으면 마지막 경로 이름(필드명) */
    static String displayName(Context context) {
        if (!(context instanceof MessageInterpolatorContext)) {
            return "";
        }
        MessageInterpolatorContext mipContext = (MessageInterpolatorContext) context;
        String propertyPath = mipContext.getPropertyPath().toString();
        Field field = findField(mipContext.getRootBeanType(), propertyPath);
        if (field != null && field.getAnnotation(DisplayName.class) != null) {
            return field.getAnnotation(DisplayName.class).value();
        }
        return lastName(propertyPath);
    }

    /** "categories[0].sortNo" 처럼 인덱스·중첩이 섞인 경로를 따라가 마지막 필드를 찾는다. 못 찾으면 null */
    static Field findField(Class<?> rootType, String propertyPath) {
        Class<?> targetType = rootType;
        Field field = null;
        for (String part : propertyPath.split("\\.")) {
            String name = stripIndex(part);
            if (name.isEmpty() || targetType == null) {
                return null;
            }
            field = declaredField(targetType, name);
            if (field == null) {
                return null;
            }
            targetType = elementType(field);
        }
        return field;
    }

    /** 상위 클래스(FileBean 등)의 필드까지 찾는다 */
    private static Field declaredField(Class<?> type, String name) {
        for (Class<?> c = type; c != null && c != Object.class; c = c.getSuperclass()) {
            try {
                return c.getDeclaredField(name);
            } catch (NoSuchFieldException ignored) {
                // 상위 클래스에서 계속 찾는다
            }
        }
        return null;
    }

    /** List<T>·T[] 필드는 요소 타입 T, 그 외는 필드 타입 */
    private static Class<?> elementType(Field field) {
        if (field.getType().isArray()) {
            return field.getType().getComponentType();
        }
        Type generic = field.getGenericType();
        if (generic instanceof ParameterizedType) {
            Type[] args = ((ParameterizedType) generic).getActualTypeArguments();
            if (args.length == 1 && args[0] instanceof Class) {
                return (Class<?>) args[0];
            }
        }
        return field.getType();
    }

    private static String stripIndex(String part) {
        return part.replaceAll("\\[[^\\]]*]", "");
    }

    private static String lastName(String propertyPath) {
        String[] parts = propertyPath.split("\\.");
        return stripIndex(parts[parts.length - 1]);
    }
}
