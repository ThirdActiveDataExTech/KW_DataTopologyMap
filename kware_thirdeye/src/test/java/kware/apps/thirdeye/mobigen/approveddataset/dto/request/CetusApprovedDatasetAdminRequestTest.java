package kware.apps.thirdeye.mobigen.approveddataset.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

/**
 * 진열 등록 요청은 데이터셋 ID·메인 UI·공개 여부·정렬 순서·카테고리·원본 위치가 있어야 하고,
 * 일괄 삭제 요청은 대상 uid 가 1개 이상이어야 한다(빈 목록은 IN () SQL 오류로 500 이 났다).
 */
class CetusApprovedDatasetAdminRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void 진열_등록_정상_요청은_위반이_없다() throws Exception {
        SaveApprovedDataset request = mapper.readValue(
                "{\"metadataId\":\"TEST-HOSP-1\",\"mainUiUid\":28,\"showAt\":\"Y\",\"sortNo\":1,\"extraJson\":\"{}\","
                + "\"category\":{\"uid\":null,\"categoryNm\":\"병원\"},\"targetTpCd\":\"MOBIGEN\"}", SaveApprovedDataset.class);

        assertThat(validator.validate(request)).isEmpty();
    }

    @Test
    void 진열_등록_필수값이_없으면_필드별로_위반이_난다() throws Exception {
        SaveApprovedDataset request = mapper.readValue("{}", SaveApprovedDataset.class);

        assertThat(paths(validator.validate(request)))
                .containsExactlyInAnyOrder("metadataId", "mainUiUid", "showAt", "sortNo", "category", "targetTpCd");
    }

    @Test
    void 카테고리_이름이_비면_중첩_경로로_위반이_난다() throws Exception {
        SaveApprovedDataset request = mapper.readValue(
                "{\"metadataId\":\"TEST-HOSP-1\",\"mainUiUid\":28,\"showAt\":\"X\",\"sortNo\":1,"
                + "\"category\":{\"uid\":null,\"categoryNm\":\" \"},\"targetTpCd\":\"MOBIGEN\"}", SaveApprovedDataset.class);

        assertThat(paths(validator.validate(request))).containsExactlyInAnyOrder("showAt", "category.categoryNm");
    }

    @Test
    void 일괄_삭제는_대상이_비면_위반이_난다() throws Exception {
        assertThat(paths(validator.validate(mapper.readValue("{\"uids\":[]}", DeleteApprovedDatasets.class)))).containsExactly("uids");
        assertThat(paths(validator.validate(mapper.readValue("{}", DeleteApprovedDatasets.class)))).containsExactly("uids");
        assertThat(validator.validate(mapper.readValue("{\"uids\":[180,181]}", DeleteApprovedDatasets.class))).isEmpty();
    }

    private static Set<String> paths(Set<? extends ConstraintViolation<?>> violations) {
        return violations.stream().map(v -> v.getPropertyPath().toString()).collect(Collectors.toSet());
    }
}
