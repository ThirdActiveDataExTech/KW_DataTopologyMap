package kware.apps.mobigen.mobigen.dto.request.relation;


import kware.apps.mobigen.mobigen.dto.request.common.PaginationRequest;
import kware.apps.mobigen.mobigen.dto.request.common.SortRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
* @fileName     SearchRelationListRequest
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-15
* @summary      [RELATION_01] 연관 메타데이터 목록 조회 요청 DTO
**/

@Getter @Setter
public class SearchRelationListRequest {

    private String action;                          // "search"
    private SearchRelationListFilters filters;
    private PaginationRequest pagination;
    private SortRequest sort;

    @Getter @Setter
    public class SearchRelationListFilters {
        private String publisher;
        private List<String> theme;
        private SimilarityScore similarity_score;

        @Getter @Setter
        public class SimilarityScore {
            private Long min;
            private Long max;
        }
    }
}
