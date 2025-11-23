package kware.apps.mobigen.mobigen;

import kware.apps.mobigen.mobigen.dto.response.ApiResponse;

public class ApiException extends RuntimeException{
    private final ApiResponse<?> response;

    public ApiException(ApiResponse<?> response) {
        super(response.getMessage());
        this.response = response;
    }

    public ApiResponse<?> getResponse() {
        return response;
    }
}
