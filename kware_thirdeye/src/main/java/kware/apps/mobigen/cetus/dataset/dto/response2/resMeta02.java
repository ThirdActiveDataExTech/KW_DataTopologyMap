package kware.apps.mobigen.cetus.dataset.dto.response2;


import kware.apps.mobigen.mobigen.dto.request.common.PaginationRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class resMeta02 {

    private String code;
    private String message;
    private resMeta02_result result;

    public resMeta02(String code, String message, resMeta02_result result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class resMeta02_result {
        private String filter;
        private List<String> values;

        public resMeta02_result(String filter, List<String> values) {
            this.filter = filter;
            this.values = values;
        }
    }
}
