package kware.apps.thirdeye.mobigen.datasetui.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

/**
 * 진열 정보 수정은 메인 UI·공개 여부·정렬 순서·카테고리가 있어야 하고(모두 NOT NULL 컬럼),
 * 공개 여부 일괄 변경은 대상 uid 가 1개 이상이어야 한다.
 */
class CetusDatasetUiAdminRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void 진열_수정_정상_요청은_위반이_없다() throws Exception {
        ChangeDatasetUi request = mapper.readValue(
                "{\"mainUiUid\":28,\"showAt\":\"N\",\"sortNo\":3,\"thumbUid\":null,\"extraJson\":\"{}\",\"category\":{\"uid\":7,\"categoryNm\":\"병원\"}}",
                ChangeDatasetUi.class);

        assertThat(validator.validate(request)).isEmpty();
    }

    @Test
    void 진열_수정_필수값이_없으면_필드별로_위반이_난다() throws Exception {
        ChangeDatasetUi request = mapper.readValue("{\"showAt\":\"maybe\"}", ChangeDatasetUi.class);

        assertThat(paths(validator.validate(request))).containsExactlyInAnyOrder("mainUiUid", "showAt", "sortNo", "category");
    }

    @Test
    void 공개_여부_일괄_변경은_대상이_비면_위반이_난다() throws Exception {
        assertThat(paths(validator.validate(mapper.readValue("{\"uids\":[]}", ChangeShowUiDatasets.class)))).containsExactly("uids");
        assertThat(validator.validate(mapper.readValue("{\"uids\":[180]}", ChangeShowUiDatasets.class))).isEmpty();
    }

    private static Set<String> paths(Set<? extends ConstraintViolation<?>> violations) {
        return violations.stream().map(v -> v.getPropertyPath().toString()).collect(Collectors.toSet());
    }
}
