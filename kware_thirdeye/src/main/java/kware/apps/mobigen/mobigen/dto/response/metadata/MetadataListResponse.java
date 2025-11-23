package kware.apps.mobigen.mobigen.dto.response.metadata;


import kware.apps.mobigen.mobigen.dto.response.common.MetadataResultResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
* @fileName     MetadataListResponse
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      [METADATA_01] 필터링된 메타데이터 목록 응답 DTO
**/

@Getter @Setter @ToString
public class MetadataListResponse {
    private int total_count;
    private int page;
    private int limit;
    private List<MetadataResultResponse> items;
}