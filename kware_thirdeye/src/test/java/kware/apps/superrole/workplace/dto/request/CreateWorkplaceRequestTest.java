package kware.apps.superrole.workplace.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import kware.apps.superrole.workplace.dto.request.createworkplace.CreateWorkplace;
import org.junit.jupiter.api.Test;

/**
 * 슈퍼 사용자의 워크플레이스 선택·생성 요청. 생성은 이름(varchar 64)과 사용자·프로그램·메뉴 목록이 있어야 하고
 * (서비스가 목록을 순회하므로 null 이면 NPE), 사용자 항목은 아이디·이름·이메일·비밀번호·권한이 있어야 한다.
 */
class CreateWorkplaceRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void 워크플레이스_선택은_uid가_있어야_한다() throws Exception {
        assertThat(paths(validator.validate(mapper.readValue("{\"workplaceNm\":\"케이웨어\"}", SetWorkplace.class)))).containsExactly("workplaceUid");
        assertThat(validator.validate(mapper.readValue("{\"workplaceUid\":22}", SetWorkplace.class))).isEmpty();
    }

    @Test
    void 생성은_이름과_다섯_목록이_모두_있어야_한다() throws Exception {
        assertThat(paths(validator.validate(mapper.readValue("{}", CreateWorkplace.class))))
                .containsExactlyInAnyOrder("workplaceNm", "users", "programs", "systemMenus", "adminMenus", "userMenus");
    }

    @Test
    void 사용자_항목의_필수값_누락과_이메일_형식은_항목_경로로_위반이_난다() throws Exception {
        CreateWorkplace request = mapper.readValue(
                "{\"workplaceNm\":\"케이웨어\",\"users\":[{\"id\":\"admin\",\"name\":\"관리자\",\"email\":\"bad-mail\",\"password\":\"x\",\"role\":\"ADMIN\"},{\"id\":\"\"}],"
              + "\"programs\":[],\"systemMenus\":[],\"adminMenus\":[],\"userMenus\":[]}", CreateWorkplace.class);

        assertThat(paths(validator.validate(request))).containsExactlyInAnyOrder(
                "users[0].email", "users[1].id", "users[1].name", "users[1].email", "users[1].password", "users[1].role");
    }

    private static Set<String> paths(Set<? extends ConstraintViolation<?>> violations) {
        return violations.stream().map(v -> v.getPropertyPath().toString()).collect(Collectors.toSet());
    }
}
