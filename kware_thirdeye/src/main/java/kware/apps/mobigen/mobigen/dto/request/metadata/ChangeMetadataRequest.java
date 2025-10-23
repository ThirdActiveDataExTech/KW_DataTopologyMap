package kware.apps.mobigen.mobigen.dto.request.metadata;


import lombok.Getter;
import lombok.Setter;

/**
* @fileName     ChangeMetadataRequest
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      [METADATA_05] 메타데이터의 업데이트 요청 DTO
**/

@Getter @Setter
public class ChangeMetadataRequest {

    private String action;                      // "update"
    private String metadata_id;
    private ChangeMetadataFieldRequest field;

    public ChangeMetadataRequest(String metadata_id, ChangeMetadataFieldRequest field) {
        this.action = "update";
        this.metadata_id = metadata_id;
        this.field = field;
    }

    @Getter @Setter
    public static class ChangeMetadataFieldRequest {
        private String title;
        private String issued;
        private String modified;
        private String identifier;
        private String publisher;
        private String keyword;
        private String landing_page;
        private String theme;
        private String access_url;

        public ChangeMetadataFieldRequest(String title) {
            this.title = title;
        }
    }
}
