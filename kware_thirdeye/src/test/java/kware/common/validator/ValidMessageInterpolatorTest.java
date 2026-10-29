package kware.common.validator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import cetus.annotation.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * {0} 치환은 목록 요소·중첩 객체 경로에서도 동작해야 하고, 필드를 못 찾아도 예외를 내지 않아야 한다.
 * 이전에는 categories[0].sortNo 같은 경로에서 NoSuchFieldException → SimpleException 이 나서
 * 검증 오류(400)가 되어야 할 요청이 예외 응답으로 바뀌었다(2026-10-29 발견).
 */
class ValidMessageInterpolatorTest {

    private final Validator validator = Validation.byDefaultProvider().configure()
            .messageInterpolator(new ValidMessageInterpolator())
            .buildValidatorFactory()
            .getValidator();

    static class Item {
        @NotNull(message = "{0} 정보를 입력해주세요.") @DisplayName("정렬 순서")
        Integer sortNo;
    }

    static class Form {
        @NotBlank(message = "{0} 정보를 입력해주세요.") @DisplayName("이름")
        String name;
        @NotNull(message = "{0} 정보를 입력해주세요.")
        Long parentUid;                 // DisplayName 없음 → 필드명으로 대체
        @Valid
        List<Item> items;
        @Valid
        Item child;
    }

    @Test
    void 목록_요소와_중첩_객체_경로에서도_DisplayName_으로_치환한다() {
        Form form = new Form();
        form.name = "x";
        form.parentUid = 1L;
        form.items = List.of(new Item());
        form.child = new Item();

        Set<ConstraintViolation<Form>> violations = validator.validate(form);

        assertThat(messages(violations, "items[0].sortNo")).containsExactly("정렬 순서 정보를 입력해주세요.");
        assertThat(messages(violations, "child.sortNo")).containsExactly("정렬 순서 정보를 입력해주세요.");
    }

    @Test
    void DisplayName_이_없으면_필드명을_쓰고_예외를_내지_않는다() {
        Form form = new Form();   // name·parentUid 비어 있음

        Set<ConstraintViolation<Form>> violations = validator.validate(form);

        assertThat(messages(violations, "name")).containsExactly("이름 정보를 입력해주세요.");
        assertThat(messages(violations, "parentUid")).containsExactly("parentUid 정보를 입력해주세요.");
    }

    private static List<String> messages(Set<ConstraintViolation<Form>> violations, String path) {
        return violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals(path))
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
    }
}
