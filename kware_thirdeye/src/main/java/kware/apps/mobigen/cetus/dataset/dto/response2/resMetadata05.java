package kware.apps.mobigen.cetus.dataset.dto.response2;

import cetus.util.DateTimeUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class resMetadata05 {

    private String code;
    private String message;
    private resMetadata05_result result;

    public resMetadata05(String code, String message, resMetadata05_result result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class resMetadata05_result {
        private String metadata_id;
        private String updated_at;

        public resMetadata05_result(String metadata_id){
            this.metadata_id = metadata_id;
            this.updated_at = DateTimeUtil.getTodayShort();
        }
    }
}
