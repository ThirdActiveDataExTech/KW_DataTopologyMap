package kware.apps.thirdeye.mobigen.mainui.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

/**
 * 메인 UI 등록은 코드·이름·유형·썸네일 사용 여부(NOT NULL 컬럼)가 있어야 하고, 개수·줄 수는 1 이상, 여부 값은 Y/N 이어야 한다.
 */
class CetusDatasetMainUiRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void 등록_정상_요청은_위반이_없다() throws Exception {
        SaveMainUi request = mapper.readValue(
                "{\"code\":\"HOME_A\",\"name\":\"홈 A\",\"maxCount\":8,\"categoryCnt\":4,\"descLineClamp\":2,\"titleLineClamp\":1,"
                + "\"thumbUseAt\":\"Y\",\"useAt\":\"Y\",\"typeCd\":\"TypeA\",\"sortNo\":1}", SaveMainUi.class);

        assertThat(validator.validate(request)).isEmpty();
    }

    @Test
    void 등록_필수값이_없으면_필드별로_위반이_난다() throws Exception {
        SaveMainUi request = mapper.readValue("{}", SaveMainUi.class);

        assertThat(paths(validator.validate(request))).containsExactlyInAnyOrder("code", "name", "typeCd", "thumbUseAt");
    }

    @Test
    void 범위_밖_값은_위반이_난다() throws Exception {
        SaveMainUi request = mapper.readValue(
                "{\"code\":\"HOME_A\",\"name\":\"홈 A\",\"maxCount\":0,\"thumbUseAt\":\"Y\",\"useAt\":\"X\",\"typeCd\":\"TypeA\"}", SaveMainUi.class);

        assertThat(paths(validator.validate(request))).containsExactlyInAnyOrder("maxCount", "useAt");
    }

    @Test
    void 수정은_이름과_썸네일_사용_여부가_있어야_한다() throws Exception {
        assertThat(paths(validator.validate(mapper.readValue("{}", ChangeMainUi.class)))).containsExactlyInAnyOrder("name", "thumbUseAt");
        assertThat(validator.validate(mapper.readValue("{\"name\":\"홈 B\",\"thumbUseAt\":\"N\",\"useAt\":\"N\",\"sortNo\":2}", ChangeMainUi.class))).isEmpty();
    }

    private static Set<String> paths(Set<? extends ConstraintViolation<?>> violations) {
        return violations.stream().map(v -> v.getPropertyPath().toString()).collect(Collectors.toSet());
    }
}
