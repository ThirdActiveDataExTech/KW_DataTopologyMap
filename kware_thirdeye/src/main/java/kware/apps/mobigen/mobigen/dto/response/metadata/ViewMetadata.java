package kware.apps.mobigen.mobigen.dto.response.metadata;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
* @fileName     ViewMetadata
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      메타데이터의 상세 정보 전달
**/

@Getter @Setter
public class ViewMetadata {

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
}
