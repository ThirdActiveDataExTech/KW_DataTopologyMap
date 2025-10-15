package kware.apps.mobigen.mobigen.dto.request.metadata;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
* @fileName     ChangeMetadata
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      메타데이터의 업데이트 진행
**/

@Getter @Setter
public class ChangeMetadata {

    private String action;                  // "update"
    private String metadata_id;
    private ChangeMetadataField field;

    @Getter @Setter
    public class ChangeMetadataField {
        private String title;
        private String issued;
        private String modified;
        private String identifier;
        private String publisher;
        private String keyword;
        private String landing_page;
        private String theme;
        private String access_url;
    }
}
