package kware.apps.mobigen.mobigen.dto.request.relation;


import kware.apps.mobigen.mobigen.dto.request.rawdata.ChangeRawdata;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
* @fileName     SearchRelationList
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-15
* @summary      연관 메타데이터 목록 조회
**/

@Getter @Setter
public class SearchRelationList {

    private String action;       // "search"
    private Filters filters;
    private Pagination pagination;
    private Sort sort;

    @Getter @Setter
    public class Filters {
        private String publisher;
        private List<String> theme;
        private SimilarityScore similarity_score;

        @Getter @Setter
        public class SimilarityScore {
            private Long min;
            private Long max;
        }
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
