package cetus.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.TitleBackfillResult;
import org.junit.jupiter.api.Test;

/**
 * 공통 ObjectMapper 가 java.time 값을 직렬화할 수 있어야 한다(없으면 상태 API 등이 500).
 */
class DefaultConfigTest {

    private final ObjectMapper mapper = new DefaultConfig().mapper();

    @Test
    void Instant_를_가진_맵을_JSON_으로_만들_수_있다() throws Exception {
        Map<String, Object> body = Collections.singletonMap("checkedAt", Instant.parse("2026-10-24T08:00:00Z"));

        String json = mapper.writeValueAsString(body);

        assertThat(json).contains("checkedAt");
    }

    @Test
    void 백필_결과의_finishedAt_은_ISO_문자열로_나간다() throws Exception {
        TitleBackfillResult result = TitleBackfillResult.skipped("모비젠 연결 불가", Instant.parse("2026-10-24T08:00:00Z"));

        String json = mapper.writeValueAsString(result);

        assertThat(json).contains("\"finishedAt\":\"2026-10-24T08:00:00Z\"");
        assertThat(json).contains("\"status\":\"SKIPPED\"");
    }
}
