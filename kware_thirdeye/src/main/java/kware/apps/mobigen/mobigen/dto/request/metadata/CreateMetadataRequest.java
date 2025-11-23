package kware.apps.mobigen.mobigen.dto.request.metadata;


import cetus.user.UserUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
* @fileName     CreateMetadata
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      [METADATA_03] 하나의 메타데이터 생성 요청 DTO
**/

@Getter @Setter
public class CreateMetadataRequest {

    private String action;                      // "create"
    private CreateMetadataFieldRequest field;

    public CreateMetadataRequest(CreateMetadataFieldRequest field) {
        this.action = "create";
        this.field = field;
    }

    @Getter @Setter
    public static class CreateMetadataFieldRequest {
        private String title;
        private String issued;
        private String modified;
        private String description;
        private String publisher;
        private String keywords;
        private String landing_page;
        private List<String> theme;
        private String access_url;
//        private Map<String, Object> custom_meta;

        public CreateMetadataFieldRequest(String title) {
            this.title = title;
            this.publisher = UserUtil.getUser().getUserId();
        }
    }
}
