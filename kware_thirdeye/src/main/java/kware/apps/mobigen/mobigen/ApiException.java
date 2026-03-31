package kware.apps.mobigen.mobigen;

import kware.apps.mobigen.mobigen.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;


public class ApiException extends RuntimeException {
    private final ApiResponse<?> response;
    private final HttpStatus httpStatus;

    public ApiException(ApiResponse<?> response, HttpStatus httpStatus) {
        super(response.getMessage());
        this.response = response;
        this.httpStatus = httpStatus;
    }

    public ApiResponse<?> getResponse() {
        return response;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
