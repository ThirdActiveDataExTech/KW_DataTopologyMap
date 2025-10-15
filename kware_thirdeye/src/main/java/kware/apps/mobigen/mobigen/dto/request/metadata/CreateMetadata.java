package kware.apps.mobigen.mobigen.dto.request.metadata;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
* @fileName     CreateMetadata
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      하나의 메타데이터 생성 요청
**/

@Getter @Setter
public class CreateMetadata {

    private String action;      // "create"
    private Field field;

    @Getter @Setter
    public static class Field {
        private String title;
        private String issued;
        private String modified;
        private String description;
        private String publisher;
        private List<String> keywords;
        private String landing_page;
        private List<String> theme;
        private String access_url;
        private Map<String, Object> custom_meta;
    }
}
