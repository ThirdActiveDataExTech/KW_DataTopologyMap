package kware.apps.mobigen.mobigen.dto.response.metadata;


import lombok.Getter;
import lombok.Setter;

/**
* @fileName     CreateMetadataResponse
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      하나의 메타데이터 생성 요청
**/

@Getter @Setter
public class CreateMetadataResponse {

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
}
