package kware.apps.mobigen.mobigen.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApiResponse<T> {

    private int code;
    private String message;
    private T result;
    private boolean isSuccess;

    public boolean isSuccess() {
        return this.code == ApiStatus.SUCCESS.getCode();
    }
}
