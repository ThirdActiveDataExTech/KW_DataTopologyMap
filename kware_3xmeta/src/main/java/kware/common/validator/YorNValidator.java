package kware.common.validator;

import cetus.annotation.YOrN;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class YorNValidator implements ConstraintValidator<YOrN, String> {

    @Override
    public void initialize(YOrN constraintAnnotation) {
        // 초기화 메서드
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if ( value == null ) {
            return true;        // {null} 처리는 @NotNull, @NotBlank, @NotEmpty 로 잡기
        }

        return ("Y".equals(value) || "N".equals(value));
    }
}