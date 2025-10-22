package kware.apps.mobigen.cetus.dataset.dto.response2;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class resMetadata07 {

    private String code;
    private String message;
    private resMetadata07_Result result;

    public resMetadata07(String code, String message, reqMetadata07_Metadata metadata) {
        this.code = code;
        this.message = message;
        this.result = new resMetadata07_Result(metadata);
    }

    @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public class resMetadata07_Result {
        private reqMetadata07_Metadata metadata;

        public resMetadata07_Result(reqMetadata07_Metadata metadata) {
            this.metadata = metadata;
        }
    }

    @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class reqMetadata07_Metadata {
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

        public reqMetadata07_Metadata(String metadata_id, String title, String keywords) {
            this.metadata_id = metadata_id;
            this.title = title;
            this.keywords = keywords;
        }
    }
}
