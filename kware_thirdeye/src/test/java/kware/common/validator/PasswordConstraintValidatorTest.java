package kware.common.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import cetus.annotation.ValidPassword;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PasswordConstraintValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"abcd123!", "Passw0rd#", "a1!aaaaa", "12345678a@", "very-long-password-9"})
    void 영문_숫자_특수문자를_모두_포함한_8자_이상이면_통과한다(String password) {
        assertThat(validator(false).isValid(password, null)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"abcdefg1", "abcdefg!", "1234567!", "ab1!", "ABCDEFGH", "12345678", "!@#$%^&*"})
    void 세_종류_중_하나라도_빠지거나_8자_미만이면_실패한다(String password) {
        assertThat(validator(false).isValid(password, null)).isFalse();
    }

    @Test
    void 비어_있는_값은_allowNull_설정을_따른다() {
        assertThat(validator(false).isValid(null, null)).isFalse();
        assertThat(validator(false).isValid("", null)).isFalse();
        assertThat(validator(true).isValid(null, null)).isTrue();
        assertThat(validator(true).isValid("", null)).isTrue();
    }

    private static PasswordConstraintValidator validator(boolean allowNull) {
        ValidPassword annotation = mock(ValidPassword.class);
        when(annotation.allowNull()).thenReturn(allowNull);
        PasswordConstraintValidator validator = new PasswordConstraintValidator();
        validator.initialize(annotation);
        return validator;
    }
}
