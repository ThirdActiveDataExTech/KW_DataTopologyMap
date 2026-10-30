package kware.apps.system.dept.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import kware.apps.system.workplace.dto.request.WorkplaceSave;
import org.junit.jupiter.api.Test;

/**
 * 부서·워크플레이스 이름은 NOT NULL varchar(64) 컬럼이다. 누락·초과는 DB 제약 위반(500)이 아니라 400 이어야 한다.
 */
class CetusDeptWorkplaceRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void 부서_등록은_이름과_워크플레이스가_있어야_한다() throws Exception {
        assertThat(paths(validator.validate(mapper.readValue("{\"sortNo\":1}", DeptTreeSave.class)))).containsExactlyInAnyOrder("deptNm", "workplaceUid");
        assertThat(validator.validate(mapper.readValue("{\"deptNm\":\"연구소\",\"sortNo\":1,\"workplaceUid\":22}", DeptTreeSave.class))).isEmpty();
    }

    @Test
    void 부서_이름은_64자를_넘을_수_없다() throws Exception {
        String longName = new String(new char[65]).replace('\0', '가');

        assertThat(paths(validator.validate(mapper.readValue("{\"deptNm\":\"" + longName + "\",\"sortNo\":1}", DeptTreeChange.class)))).containsExactly("deptNm");
        assertThat(paths(validator.validate(mapper.readValue("{\"deptNm\":\"  \"}", DeptTreeChange.class)))).containsExactly("deptNm");
    }

    @Test
    void 워크플레이스_이름은_필수다() throws Exception {
        assertThat(paths(validator.validate(mapper.readValue("{}", WorkplaceSave.class)))).containsExactly("workplaceNm");
        assertThat(validator.validate(mapper.readValue("{\"workplaceNm\":\"케이웨어\"}", WorkplaceSave.class))).isEmpty();
    }

    private static Set<String> paths(Set<? extends ConstraintViolation<?>> violations) {
        return violations.stream().map(v -> v.getPropertyPath().toString()).collect(Collectors.toSet());
    }
}
