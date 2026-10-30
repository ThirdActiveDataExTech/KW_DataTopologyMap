package kware.apps.system.invite.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

/**
 * 회원 초대는 받는 이메일이 있어야 하고 형식이 맞아야 메일을 보낸다(컬럼 NOT NULL varchar(255)).
 */
class CetusInviteRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void 이메일이_없거나_형식이_틀리면_위반이_난다() throws Exception {
        assertThat(paths(validator.validate(mapper.readValue("{}", CetusInvite.class)))).containsExactly("email");
        assertThat(paths(validator.validate(mapper.readValue("{\"email\":\"not-an-address\"}", CetusInvite.class)))).containsExactly("email");
    }

    @Test
    void 올바른_이메일은_위반이_없다() throws Exception {
        assertThat(validator.validate(mapper.readValue("{\"email\":\"invitee@example.com\"}", CetusInvite.class))).isEmpty();
    }

    private static Set<String> paths(Set<? extends ConstraintViolation<?>> violations) {
        return violations.stream().map(v -> v.getPropertyPath().toString()).collect(Collectors.toSet());
    }
}
