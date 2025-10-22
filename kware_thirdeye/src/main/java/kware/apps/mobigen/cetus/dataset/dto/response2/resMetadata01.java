package kware.apps.mobigen.cetus.dataset.dto.response2;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class resMetadata01 {

    private String code;
    private String message;
    private resMetadata01_result result;

    public resMetadata01(String code, String message, resMetadata01_result result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class resMetadata01_result {
        private int total_count;
        private int page;
        private int limit;
        private List<resMetadata01_items> items;

        public resMetadata01_result(int total_count, int page, int limit, List<resMetadata01_items> items) {
            this.total_count = total_count;
            this.page = page;
            this.limit = limit;
            this.items = items;
        }

        @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class resMetadata01_items {
            private String metadata_id;
            private String title;
            private String issued;
            private String modified;
            private String identifier;
            private String publisher;
            private String keywords;
            private String landing_page;
            private String theme;
            private String access_url;
            private String value_a;
            private String domain;
            private String ingested_at;
            private String updated_at;

            public resMetadata01_items(String metadata_id, String title, String keywords) {
                this.metadata_id = metadata_id;
                this.title = title;
                this.keywords = keywords;
            }
        }
    }
}
