package kware.apps.mobigen.mobigen.dto.request.recommendation;


import kware.apps.mobigen.mobigen.dto.request.common.PaginationRequest;
import kware.apps.mobigen.mobigen.dto.request.common.SortFieldRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
* @fileName     SearchRecommendationListRequest
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-15
* @summary      [RECOMMENDATION_01] 추천 메타데이터 목록 조회 요청 DTO
**/

@Getter @Setter @ToString
public class SearchRecommendationListRequest {

    private String action;                              // "search"
    private SearchRecommendationListFilters filters;
    private PaginationRequest pagination;
    private SortFieldRequest sort;

    public SearchRecommendationListRequest(List<String> recommendation_type, String publisher, List<String> theme,
                                           int page, int limit,
                                           String sortOrder, String sortField ) {
        this.action = "search";
        this.filters = new SearchRecommendationListFilters(recommendation_type, publisher, theme);
        this.pagination = new PaginationRequest(page, limit, false);
        this.sort = new SortFieldRequest(sortOrder, sortField);
    }
}
