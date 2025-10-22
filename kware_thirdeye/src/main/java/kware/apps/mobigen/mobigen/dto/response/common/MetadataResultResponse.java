package kware.apps.mobigen.mobigen.dto.response.common;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName     MetadataResultResponse
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-16
* @summary      [COMMON] 메타 데이터에 대한 공통 응답 DTO
**/

@Getter @Setter
public class MetadataResultResponse {

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

}
