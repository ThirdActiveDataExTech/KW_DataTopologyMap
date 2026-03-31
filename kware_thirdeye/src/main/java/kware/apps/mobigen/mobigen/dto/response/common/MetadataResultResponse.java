package kware.apps.mobigen.mobigen.dto.response.common;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
* @fileName     MetadataResultResponse
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-16
* @summary      [COMMON] 메타 데이터에 대한 공통 응답 DTO
**/

@Getter @Setter @ToString
public class MetadataResultResponse {
    private String metadata_id;
    private String title;
    private String issued;
    private String modified;
    private String identifier;
    private String publisher;
    private String keywords;         // ex. "요양기관, 요양기관현황, 의료장비, 의료시설, 의료자원"
    private String landing_page;
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<String> theme;
    private String access_url;
    private String ingested_at;
    private String updated_at;

    private String raw_metadata;
}
