package kware.apps.mobigen.cetus.dataset.dto.response2;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class resMeta01 {

    private String code;
    private String message;
    private resMeta01_result result;

    public resMeta01(String code, String message, resMeta01_result result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class resMeta01_result {
        private List<String> filters;

        public resMeta01_result(List<String> filters) {
            this.filters = filters;
        }
    }
}
