package kware.apps.mobigen.mobigen.dto.response.relation;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName     RelationListResponse
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      [RELATION_01] 연관 메타데이터 목록 조회 응답 DTO
**/

@Getter @Setter
public class RelationListResponse {
    private String metadata_id;
    private Integer total_count;
    private Integer page;
    private Integer limit;
    private List<RelatedMetadataResponse> items;
    private List<FieldRelationsResponse> field_relations;
}
