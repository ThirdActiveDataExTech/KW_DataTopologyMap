package kware.common.exception;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import kware.apps.mobigen.mobigen.ApiException;
import kware.apps.mobigen.mobigen.dto.response.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * API 예외는 상태 코드와 본문 형식이 호출 화면(JS)과 약속된 값이어야 한다.
 */
@SuppressWarnings("unchecked")
class RestExceptionHandlerTest {

    private final RestExceptionHandler handler = new RestExceptionHandler(messages());

    @Test
    void 검증_오류는_400과_필드별_메시지_맵이다() throws Exception {
        BeanPropertyBindingResult binding = new BeanPropertyBindingResult(new SampleForm(), "sampleForm");
        binding.addError(new FieldError("sampleForm", "title", "제목은 필수입니다."));
        binding.addError(new FieldError("sampleForm", "size", "0보다 커야 합니다."));

        ResponseEntity<?> entity = handler.requestBodyhandleValidException(
                new MethodArgumentNotValidException(sampleParameter(), binding));

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat((Map<String, String>) entity.getBody())
                .containsEntry("title", "제목은 필수입니다.")
                .containsEntry("size", "0보다 커야 합니다.")
                .hasSize(2);
    }

    @Test
    void 같은_필드의_오류가_여럿이면_br로_이어_붙인다() throws Exception {
        BeanPropertyBindingResult binding = new BeanPropertyBindingResult(new SampleForm(), "sampleForm");
        binding.addError(new FieldError("sampleForm", "title", "첫째"));
        binding.addError(new FieldError("sampleForm", "title", "둘째"));

        ResponseEntity<?> entity = handler.requestBodyhandleValidException(
                new MethodArgumentNotValidException(sampleParameter(), binding));

        assertThat((Map<String, String>) entity.getBody()).containsEntry("title", "첫째<br>둘째");
    }

    @Test
    void 폼_바인딩_오류_BindException도_같은_형식으로_돌려준다() {
        BeanPropertyBindingResult binding = new BeanPropertyBindingResult(new SampleForm(), "sampleForm");
        binding.addError(new FieldError("sampleForm", "size", "숫자만 입력하세요."));

        ResponseEntity<?> entity = handler.requestBodyhandleValidException(new BindException(binding));

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat((Map<String, String>) entity.getBody()).containsEntry("size", "숫자만 입력하세요.").hasSize(1);
    }

    @Test
    void SimpleException은_담긴_상태_코드와_메시지를_그대로_돌려준다() {
        ResponseEntity<?> conflict = handler.simpleException(new SimpleException("이미 있는 이름", HttpStatus.CONFLICT));
        ResponseEntity<?> defaultStatus = handler.simpleException(new SimpleException("실패"));

        assertThat(conflict.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(conflict.getBody()).isEqualTo("이미 있는 이름");
        assertThat(defaultStatus.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(defaultStatus.getBody()).isEqualTo("실패");
    }

    @Test
    void ApiException은_모비젠_응답_본문과_상태_코드를_그대로_전달한다() {
        ApiResponse<Object> response = new ApiResponse<>();
        response.setCode(9999);
        response.setMessage("메타데이터 없음");

        ResponseEntity<?> entity = handler.apiException(new ApiException(response, HttpStatus.BAD_GATEWAY));

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_GATEWAY);
        assertThat(entity.getBody()).isSameAs(response);
    }

    @Test
    void NoSuchElement는_404와_메시지_번들_문구다() {
        ResponseEntity<?> entity = handler.handleNoSuchElementException(new NoSuchElementException("row 17"));

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(entity.getBody()).isEqualTo("데이터가 없습니다.");
    }

    @Test
    void 본문을_읽을_수_없으면_400과_안내_메시지이고_내부_오류_문구는_싣지_않는다() {
        ResponseEntity<?> entity = handler.requestBodyNotReadable(
                new HttpMessageNotReadableException("JSON parse error: Unexpected end-of-input", new MockHttpInputMessage(new byte[0])));

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(entity.getBody()).isEqualTo("요청 본문 형식이 올바르지 않습니다.");
    }

    private static MessageSourceAccessor messages() {
        StaticMessageSource source = new StaticMessageSource();
        source.addMessage("http.error.empty", Locale.getDefault(), "데이터가 없습니다.");
        source.addMessage("http.error.bad-request-body", Locale.getDefault(), "요청 본문 형식이 올바르지 않습니다.");
        return new MessageSourceAccessor(source, Locale.getDefault());
    }

    private static MethodParameter sampleParameter() throws NoSuchMethodException {
        return new MethodParameter(RestExceptionHandlerTest.class.getDeclaredMethod("sample", SampleForm.class), 0);
    }

    @SuppressWarnings("unused")
    private void sample(SampleForm form) {
    }

    static class SampleForm {
        private String title;
        private int size;

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public int getSize() { return size; }
        public void setSize(int size) { this.size = size; }
    }
}
