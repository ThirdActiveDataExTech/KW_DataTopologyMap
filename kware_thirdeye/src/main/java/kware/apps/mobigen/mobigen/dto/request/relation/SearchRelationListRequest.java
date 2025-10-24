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

    public SearchRelationListRequest( String publisher, List<String> theme,
                                      Long min, Long max,
                                      int page, int limit,
                                      String order ) {
        this.action = "search";
        this.filters = new SearchRelationListFilters(publisher, theme, new SearchRelationListFilters.SimilarityScore(min, max));
        this.pagination = new PaginationRequest(page, limit);
        this.sort = new SortRequest(order);
    }

    @Getter @Setter
    public static class SearchRelationListFilters {
        private String publisher;
        private List<String> theme;
        private SimilarityScore similarity_score;

        public SearchRelationListFilters(String publisher, List<String> theme, SimilarityScore similarity_score) {
            this.publisher = publisher;
            this.theme = theme;
            this.similarity_score = similarity_score;
        }

        @Getter @Setter
        public static class SimilarityScore {
            private Long min;
            private Long max;

            public SimilarityScore(Long min, Long max) {
                this.min = min;
                this.max = max;
            }
        }
    }
}
