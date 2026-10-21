package kware.common.exception;

import kware.common.config.filter.RequestIdFilter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 화면·API 공통의 마지막 예외 처리. 세부 예외는 RestExceptionHandler 가 먼저 받는다(@Order).
 * 예외 메시지·스택은 로그에만 남기고 응답에는 고정 문구와 요청 추적 ID만 돌려준다.
 */
@Slf4j
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class HttpExceptionHandler {

    @Value("${spring.servlet.multipart.max-file-size}")
    private DataSize maxFileSize;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleNotFoundException(ResourceNotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("error/404");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Object handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        if (wantsJson(request)) {
            // ex.getMessage() 에는 클래스명·입력값이 들어 있어 그대로 내보내지 않는다
            return ResponseEntity.badRequest().body(body("잘못된 요청 값: " + ex.getName()));
        }
        ModelAndView modelAndView = new ModelAndView("error/404");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Object handleMaxUploadSize(MaxUploadSizeExceededException ex, HttpServletRequest request) {
        String message = "업로드 가능한 최대 크기는 " + maxFileSize.toMegabytes() + "MB 입니다.";
        if (wantsJson(request)) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(body(message));
        }
        ModelAndView modelAndView = new ModelAndView("error/error");
        modelAndView.setStatus(HttpStatus.PAYLOAD_TOO_LARGE);
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public Object handleUnexpected(Exception ex, HttpServletRequest request) throws Exception {
        // 인증·인가 예외는 Spring Security 의 ExceptionTranslationFilter 가 처리해야 한다(로그인 이동, 403)
        if (ex instanceof AccessDeniedException || ex instanceof AuthenticationException) {
            throw ex;
        }
        log.error("처리되지 않은 예외 uri={} method={}", request.getRequestURI(), request.getMethod(), ex);
        if (wantsJson(request)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body("처리 중 오류가 발생했습니다."));
        }
        ModelAndView modelAndView = new ModelAndView("error/500");
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return modelAndView;
    }

    private boolean wantsJson(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        return (accept != null && accept.contains("application/json"))
                || "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    private Map<String, String> body(String message) {
        Map<String, String> body = new LinkedHashMap<>();
        body.put("message", message);
        body.put("requestId", MDC.get(RequestIdFilter.MDC_KEY));
        return body;
    }
}
