package kware.apps.mobigen.cetus.dataset.dto.response2;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class resPackage02 {

    private String code;
    private String message;
    private resPackage02_Result result;

    public resPackage02(String code, String message, resPackage02_Metadata metadata) {
        this.code = code;
        this.message = message;
        this.result = new resPackage02_Result(metadata);
    }

    @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public class resPackage02_Result {
        private resPackage02_Metadata metadata;

        public resPackage02_Result(resPackage02_Metadata metadata) {
            this.metadata = metadata;
        }
    }

    @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class resPackage02_Metadata {
        private String metadata_id;
        private String title;
        private String issued;
        private String modified;
        private String identifier;
        private String publisher;
        private String keyword;
        private List<String> keywords;
        private String landing_page;
        private String theme;
        private String access_url;
        private String value_a;
        private String domain;
        private String ingested_at;
        private String updated_at;

        public resPackage02_Metadata(String metadata_id, String title, List<String> keywords) {
            this.metadata_id = metadata_id;
            this.title = title;
            this.keywords = keywords;
        }
    }
}
