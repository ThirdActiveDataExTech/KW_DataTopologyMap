package kware.apps.thirdeye.comment.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

/**
 * 평점·의견 등록 요청은 데이터셋·유형·내용이 있어야 하고 평점은 0~5, 내용은 1000자 이내여야 한다.
 * 요청 본문은 화면과 같이 JSON 으로 만들어 검증한다.
 */
class DatasetCommentSaveTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void 정상_요청은_위반이_없다() throws Exception {
        DatasetCommentSave request = read("{\"approvedUid\":180,\"typeCd\":\"COMMENT_OPINION\",\"ratings\":5,\"comment\":\"좋은 데이터셋\"}");

        assertThat(validator.validate(request)).isEmpty();
    }

    @Test
    void 필수값이_없으면_필드별로_위반이_난다() throws Exception {
        DatasetCommentSave request = read("{\"comment\":\"   \"}");

        assertThat(paths(validator.validate(request))).containsExactlyInAnyOrder("approvedUid", "typeCd", "comment");
    }

    @Test
    void 평점_범위와_내용_길이를_넘으면_위반이_난다() throws Exception {
        String longComment = "a".repeat(DatasetCommentSave.MAX_COMMENT_LENGTH + 1);
        DatasetCommentSave request = read("{\"approvedUid\":180,\"typeCd\":\"COMMENT_OPINION\",\"ratings\":6,\"comment\":\"" + longComment + "\"}");

        assertThat(paths(validator.validate(request))).containsExactlyInAnyOrder("ratings", "comment");
    }

    @Test
    void 평점은_없거나_0_이어도_된다() throws Exception {
        assertThat(validator.validate(read("{\"approvedUid\":180,\"typeCd\":\"COMMENT_QUESTION\",\"comment\":\"문의\"}"))).isEmpty();
        assertThat(validator.validate(read("{\"approvedUid\":180,\"typeCd\":\"COMMENT_QUESTION\",\"ratings\":0,\"comment\":\"문의\"}"))).isEmpty();
    }

    private DatasetCommentSave read(String json) throws Exception {
        return mapper.readValue(json, DatasetCommentSave.class);
    }

    private static Set<String> paths(Set<ConstraintViolation<DatasetCommentSave>> violations) {
        return violations.stream().map(v -> v.getPropertyPath().toString()).collect(Collectors.toSet());
    }
}
