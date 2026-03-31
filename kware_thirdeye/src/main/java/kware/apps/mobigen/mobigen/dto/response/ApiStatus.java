package kware.apps.mobigen.mobigen.dto.response;

import lombok.Getter;

@Getter
public enum ApiStatus {
    SUCCESS(100200),
    ERROR(400001);

    private final int code;

    ApiStatus(int code) {
        this.code = code;
    }
    public static ApiStatus fromCode(int code) {
        for (ApiStatus status : values()) {
            if (status.code == code) return status;
        }
        return ERROR; // 또는 예외 발생
    }
}
