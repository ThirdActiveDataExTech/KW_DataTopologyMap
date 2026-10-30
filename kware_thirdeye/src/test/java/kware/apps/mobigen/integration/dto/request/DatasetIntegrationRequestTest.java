package kware.apps.mobigen.integration.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import kware.apps.mobigen.integration.dto.request.metadata.DeleteMetadatas;
import kware.apps.mobigen.integration.dto.request.rawdata.ChangeRawdata;
import kware.apps.mobigen.integration.dto.request.rawdata.DeleteRawdatas;
import kware.apps.mobigen.integration.dto.request.rawdata.SearchRawdataView;
import org.junit.jupiter.api.Test;

/**
 * 모비젠 연동 삭제·조회·수정 요청은 대상 ID 가 있어야 외부 API 를 호출한다.
 * 빈 목록·빈 ID 는 외부 호출 전에 400 으로 끝나야 한다.
 */
class DatasetIntegrationRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void 메타데이터_삭제는_대상이_비면_위반이_난다() throws Exception {
        assertThat(paths(validator.validate(mapper.readValue("{}", DeleteMetadatas.class)))).containsExactly("uids");
        assertThat(paths(validator.validate(mapper.readValue("{\"uids\":[]}", DeleteMetadatas.class)))).containsExactly("uids");
        assertThat(validator.validate(mapper.readValue("{\"uids\":[\"M-1\",\"M-2\"]}", DeleteMetadatas.class))).isEmpty();
    }

    @Test
    void 원본데이터_삭제는_메타데이터_ID와_대상_목록이_있어야_한다() throws Exception {
        DeleteRawdatas empty = mapper.readValue("{\"metadataId\":\" \",\"rawdataIds\":[]}", DeleteRawdatas.class);

        assertThat(paths(validator.validate(empty))).containsExactlyInAnyOrder("metadataId", "rawdataIds");
        assertThat(validator.validate(mapper.readValue("{\"metadataId\":\"M-1\",\"rawdataIds\":[\"R-1\"]}", DeleteRawdatas.class))).isEmpty();
    }

    @Test
    void 원본데이터_조회는_두_ID가_모두_있어야_한다() throws Exception {
        assertThat(paths(validator.validate(mapper.readValue("{}", SearchRawdataView.class)))).containsExactlyInAnyOrder("metadataId", "rawdataId");
        assertThat(validator.validate(mapper.readValue("{\"metadataId\":\"M-1\",\"rawdataId\":\"R-1\"}", SearchRawdataView.class))).isEmpty();
    }

    @Test
    void 원본데이터_수정은_두_ID가_있어야_하고_확장_정보는_비어도_된다() throws Exception {
        assertThat(paths(validator.validate(mapper.readValue("{\"extdata\":\"{}\"}", ChangeRawdata.class)))).containsExactlyInAnyOrder("metadataId", "rawdataId");
        assertThat(validator.validate(mapper.readValue("{\"metadataId\":\"M-1\",\"rawdataId\":\"R-1\"}", ChangeRawdata.class))).isEmpty();
    }

    private static Set<String> paths(Set<? extends ConstraintViolation<?>> violations) {
        return violations.stream().map(v -> v.getPropertyPath().toString()).collect(Collectors.toSet());
    }
}
