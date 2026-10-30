package kware.apps.system.group.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import kware.apps.system.position.dto.request.PositionMergeList;
import org.junit.jupiter.api.Test;

/**
 * 그룹·직위 병합 요청은 워크플레이스와 항목 목록이 있어야 하고, 항목 이름은 NOT NULL varchar(64) 컬럼이다.
 * 삭제 대상(uids)은 생략할 수 있다.
 */
class GroupPositionMergeRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void 그룹_병합은_워크플레이스와_목록이_없으면_위반이_난다() throws Exception {
        assertThat(paths(validator.validate(mapper.readValue("{}", GroupMergeList.class)))).containsExactlyInAnyOrder("workplaceUid", "groups");
    }

    @Test
    void 그룹_항목_이름이_비면_항목_경로로_위반이_난다() throws Exception {
        GroupMergeList request = mapper.readValue("{\"workplaceUid\":22,\"groups\":[{\"groupNm\":\"개발\",\"sortNo\":1},{\"groupNm\":\"\",\"sortNo\":2}]}", GroupMergeList.class);

        assertThat(paths(validator.validate(request))).containsExactly("groups[1].groupNm");
    }

    @Test
    void 삭제_대상을_생략한_정상_요청은_위반이_없다() throws Exception {
        assertThat(validator.validate(mapper.readValue("{\"workplaceUid\":22,\"groups\":[]}", GroupMergeList.class))).isEmpty();
        assertThat(validator.validate(mapper.readValue("{\"workplaceUid\":22,\"positions\":[{\"positionNm\":\"선임\",\"sortNo\":1}],\"uids\":[3]}", PositionMergeList.class))).isEmpty();
    }

    @Test
    void 직위_병합도_같은_규칙을_따른다() throws Exception {
        assertThat(paths(validator.validate(mapper.readValue("{\"positions\":[{\"positionNm\":\" \"}]}", PositionMergeList.class))))
                .containsExactlyInAnyOrder("workplaceUid", "positions[0].positionNm");
    }

    private static Set<String> paths(Set<? extends ConstraintViolation<?>> violations) {
        return violations.stream().map(v -> v.getPropertyPath().toString()).collect(Collectors.toSet());
    }
}
