package kware.apps.mobigen.mobigen.dto.request.recommendation;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName     SearchRecommendationList
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-15
* @summary      추천 메타데이터 목록 조회
**/

@Getter @Setter
public class SearchRecommendationList {

    private String action;
    private Filters filters;
    private Pagination pagination;
    private Sort sort;

    @Getter @Setter
    public class Filters {
        private List<String> recommendation_type;
        private String publisher;
        private List<String> theme;
    }

    @Getter @Setter
    public class Pagination {
        private int page;
        private int limit;
    }

    @Getter @Setter
    public class Sort {
        private String field;
        private String order;
    }
}
