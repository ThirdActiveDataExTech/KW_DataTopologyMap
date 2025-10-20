package kware.apps.mobigen.mobigen.dto.request.recommendation;


import kware.apps.mobigen.mobigen.dto.request.common.PaginationRequest;
import kware.apps.mobigen.mobigen.dto.request.common.SortRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName     SearchRecommendationListRequest
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-15
* @summary      [RECOMMENDATION_01] 추천 메타데이터 목록 조회 요청 DTO
**/

@Getter @Setter
public class SearchRecommendationListRequest {

    private String action;                              // "search"
    private SearchRecommendationListFilters filters;
    private PaginationRequest pagination;
    private SortRequest sort;

    @Getter @Setter
    public static class SearchRecommendationListFilters {
        private List<String> recommendation_type;
        private String publisher;
        private List<String> theme;
    }
}
