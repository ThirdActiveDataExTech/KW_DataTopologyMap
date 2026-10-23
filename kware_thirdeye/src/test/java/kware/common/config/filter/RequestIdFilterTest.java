package kware.common.config.filter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.MDC;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * 추적 ID 는 체인 안에서는 MDC 에 있고, 끝나면 지워져야 한다. 프록시 값은 허용 문자·길이 안에서만 그대로 쓴다.
 */
class RequestIdFilterTest {

    private final RequestIdFilter filter = new RequestIdFilter();

    @AfterEach
    void clearMdc() {
        MDC.clear();
    }

    @Test
    void 헤더가_없으면_새_ID를_만들어_응답_헤더로_돌려준다() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/portal/main");
        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilter(request, response, new MockFilterChain());

        String id = response.getHeader(RequestIdFilter.HEADER);
        assertThat(id).isNotNull().hasSize(16).matches("[0-9a-f]+");
    }

    @Test
    void 허용_패턴에_맞는_헤더_값은_그대로_쓴다() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/portal/main");
        request.addHeader(RequestIdFilter.HEADER, "proxy-abc.123_X");
        MockHttpServletResponse response = new MockHttpServletResponse();
        AtomicReference<String> mdcInChain = new AtomicReference<>();

        filter.doFilter(request, response, (req, res) -> mdcInChain.set(MDC.get(RequestIdFilter.MDC_KEY)));

        assertThat(response.getHeader(RequestIdFilter.HEADER)).isEqualTo("proxy-abc.123_X");
        assertThat(mdcInChain.get()).isEqualTo("proxy-abc.123_X");
    }

    @ParameterizedTest(name = "[{0}]")
    @ValueSource(strings = {"", " ", "abc def", "id\nnext=line", "한글", "a/b", "<script>",
            "0123456789012345678901234567890123456789012345678901234567890123x"})
    void 허용_패턴_밖_헤더_값은_버리고_새로_발급한다(String tainted) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/portal/main");
        request.addHeader(RequestIdFilter.HEADER, tainted);
        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilter(request, response, new MockFilterChain());

        String id = response.getHeader(RequestIdFilter.HEADER);
        assertThat(id).isNotEqualTo(tainted).hasSize(16).matches("[0-9a-f]+");
    }

    @Test
    void 체인이_끝나면_MDC에서_지운다() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/portal/main");
        AtomicReference<String> mdcInChain = new AtomicReference<>();

        filter.doFilter(request, new MockHttpServletResponse(),
                (req, res) -> mdcInChain.set(MDC.get(RequestIdFilter.MDC_KEY)));

        assertThat(mdcInChain.get()).isNotNull();
        assertThat(MDC.get(RequestIdFilter.MDC_KEY)).isNull();
    }

    @Test
    void 체인에서_예외가_나도_MDC를_지운다() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/portal/main");

        try {
            filter.doFilter(request, new MockHttpServletResponse(), (req, res) -> {
                throw new IllegalStateException("boom");
            });
        } catch (Exception expected) {
            // 예외는 그대로 전파되어야 한다
            assertThat(expected).isInstanceOf(IllegalStateException.class);
        }

        assertThat(MDC.get(RequestIdFilter.MDC_KEY)).isNull();
    }

    @Test
    void 요청마다_다른_ID를_만든다() throws Exception {
        MockHttpServletResponse first = new MockHttpServletResponse();
        MockHttpServletResponse second = new MockHttpServletResponse();

        filter.doFilter(new MockHttpServletRequest("GET", "/a"), first, new MockFilterChain());
        filter.doFilter(new MockHttpServletRequest("GET", "/b"), second, new MockFilterChain());

        assertThat(first.getHeader(RequestIdFilter.HEADER)).isNotEqualTo(second.getHeader(RequestIdFilter.HEADER));
    }
}
