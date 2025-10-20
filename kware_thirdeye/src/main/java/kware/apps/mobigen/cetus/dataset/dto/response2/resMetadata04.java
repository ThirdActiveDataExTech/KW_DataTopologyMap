package kware.apps.mobigen.cetus.dataset.dto.response2;


import cetus.util.DateTimeUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Random;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class resMetadata04 {

    private String code;
    private String message;
    private resMetadata04_result result;

    public resMetadata04(String code, String  message, resMetadata04_result result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class resMetadata04_result {
        private String metadata_id;
        private String title;
        private String issued;
        private String modified;
        private String identifier;
        private String publisher;
        private String keyword;
        private String landing_page;
        private String theme;
        private String access_url;
        private String ingested_at;
        private String updated_at;
        private Map<String, Object> raw_metadata;

        public resMetadata04_result(String title, String keyword) {
            Random random = new Random();
            int randomNum = random.nextInt(5) + 1;
            this.metadata_id = "metadata_" + randomNum;
            this.title = title;
            this.keyword = keyword;
            this.ingested_at = DateTimeUtil.getTodayShort();
            this.updated_at  = DateTimeUtil.getTodayShort();
        }
    }
}
