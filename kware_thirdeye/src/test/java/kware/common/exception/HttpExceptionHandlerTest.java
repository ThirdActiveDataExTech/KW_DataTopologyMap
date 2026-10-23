package kware.common.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;
import kware.common.config.filter.RequestIdFilter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.unit.DataSize;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

/**
 * 화면 요청은 오류 페이지, JSON·XHR 요청은 고정 문구 + requestId 만 돌려준다. 예외 내부 정보는 응답에 나오면 안 된다.
 */
@SuppressWarnings("unchecked")
class HttpExceptionHandlerTest {

    private final HttpExceptionHandler handler = new HttpExceptionHandler();

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(handler, "maxFileSize", DataSize.ofMegabytes(50));
        MDC.put(RequestIdFilter.MDC_KEY, "req-0001");
    }

    @AfterEach
    void tearDown() {
        MDC.clear();
    }

    @Test
    void 리소스_없음은_404_페이지다() {
        ModelAndView mav = handler.handleNotFoundException(new ResourceNotFoundException("missing"));

        assertThat(mav.getViewName()).isEqualTo("error/404");
        assertThat(mav.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void 타입_불일치는_JSON_요청이면_400과_파라미터_이름만_돌려준다() {
        MethodArgumentTypeMismatchException ex = typeMismatch("abc", Long.class, "fileId");

        Object result = handler.handleMethodArgumentTypeMismatchException(ex, jsonRequest());

        ResponseEntity<?> entity = (ResponseEntity<?>) result;
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Map<String, String> body = (Map<String, String>) entity.getBody();
        assertThat(body).containsEntry("message", "잘못된 요청 값: fileId").containsEntry("requestId", "req-0001");
        assertThat(body.toString()).doesNotContain("abc").doesNotContain("Long");
    }

    @Test
    void 타입_불일치는_화면_요청이면_404_페이지다() {
        MethodArgumentTypeMismatchException ex = typeMismatch("abc", Long.class, "fileId");

        Object result = handler.handleMethodArgumentTypeMismatchException(ex, htmlRequest());

        ModelAndView mav = (ModelAndView) result;
        assertThat(mav.getViewName()).isEqualTo("error/404");
        assertThat(mav.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void 업로드_크기_초과는_413과_설정된_한도를_알려준다() {
        MaxUploadSizeExceededException ex = new MaxUploadSizeExceededException(60L * 1024 * 1024);

        ResponseEntity<?> json = (ResponseEntity<?>) handler.handleMaxUploadSize(ex, xhrRequest());
        ModelAndView html = (ModelAndView) handler.handleMaxUploadSize(ex, htmlRequest());

        assertThat(json.getStatusCode()).isEqualTo(HttpStatus.PAYLOAD_TOO_LARGE);
        assertThat((Map<String, String>) json.getBody()).containsEntry("message", "업로드 가능한 최대 크기는 50MB 입니다.");
        assertThat(html.getViewName()).isEqualTo("error/error");
        assertThat(html.getStatus()).isEqualTo(HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @Test
    void 예상_못한_예외는_500과_고정_문구만_돌려준다() throws Exception {
        RuntimeException ex = new IllegalStateException("db password=secret at /internal/path");

        ResponseEntity<?> json = (ResponseEntity<?>) handler.handleUnexpected(ex, jsonRequest());
        ModelAndView html = (ModelAndView) handler.handleUnexpected(ex, htmlRequest());

        assertThat(json.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        Map<String, String> body = (Map<String, String>) json.getBody();
        assertThat(body).containsEntry("message", "처리 중 오류가 발생했습니다.").containsEntry("requestId", "req-0001");
        assertThat(body.toString()).doesNotContain("secret").doesNotContain("/internal/path");
        assertThat(html.getViewName()).isEqualTo("error/500");
        assertThat(html.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(html.getModel()).isEmpty();
    }

    @Test
    void 인증_인가_예외는_다시_던져_시큐리티가_처리하게_한다() {
        assertThatThrownBy(() -> handler.handleUnexpected(new AccessDeniedException("denied"), jsonRequest()))
                .isInstanceOf(AccessDeniedException.class);
        assertThatThrownBy(() -> handler.handleUnexpected(new BadCredentialsException("bad"), htmlRequest()))
                .isInstanceOf(BadCredentialsException.class);
    }

    @Test
    void Accept에_json이_있거나_XHR_헤더가_있으면_JSON_응답이다() throws Exception {
        RuntimeException ex = new RuntimeException("x");

        assertThat(handler.handleUnexpected(ex, jsonRequest())).isInstanceOf(ResponseEntity.class);
        assertThat(handler.handleUnexpected(ex, xhrRequest())).isInstanceOf(ResponseEntity.class);
        assertThat(handler.handleUnexpected(ex, htmlRequest())).isInstanceOf(ModelAndView.class);
        assertThat(handler.handleUnexpected(ex, new MockHttpServletRequest("GET", "/x"))).isInstanceOf(ModelAndView.class);
    }

    private static MethodArgumentTypeMismatchException typeMismatch(Object value, Class<?> requiredType, String name) {
        return new MethodArgumentTypeMismatchException(value, requiredType, name, null, new NumberFormatException(String.valueOf(value)));
    }

    private static MockHttpServletRequest jsonRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/portal/list");
        request.addHeader("Accept", "application/json, text/plain, */*");
        return request;
    }

    private static MockHttpServletRequest xhrRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/portal/save");
        request.addHeader("Accept", "*/*");
        request.addHeader("X-Requested-With", "XMLHttpRequest");
        return request;
    }

    private static MockHttpServletRequest htmlRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/portal/main");
        request.addHeader("Accept", "text/html,application/xhtml+xml");
        return request;
    }
}
