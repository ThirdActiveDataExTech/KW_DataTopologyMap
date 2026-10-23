package kware.common.exception;

import cetus.Response;
import cetus.exception.CetusValidException;
import kware.apps.mobigen.mobigen.ApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
@Order(0)   // HttpExceptionHandler 의 Exception 포괄 처리보다 먼저 평가된다
@Slf4j
@RequiredArgsConstructor
public class RestExceptionHandler {

    private final MessageSourceAccessor messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)   // MethodArgumentNotValidException 도 BindException 의 하위라 함께 받는다
    public ResponseEntity requestBodyhandleValidException(BindException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(getValidErrors(ex.getBindingResult()));
    }

    @ExceptionHandler(SimpleException.class)
    public ResponseEntity simpleException(SimpleException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(e.getMessage());
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity apiException(ApiException e) {
        return ResponseEntity
                    .status(e.getHttpStatus())
                    .body(e.getResponse());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(messageSource.getMessage("http.error.empty"));
    }


    //bad
    private Map<String, String> getValidErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField, // Key is the field name
                        FieldError::getDefaultMessage, // Value is the default error message
                        (existingMessage, newMessage) -> existingMessage + "<br>" + newMessage // Merge function for duplicate keys
                ));
    }
}
