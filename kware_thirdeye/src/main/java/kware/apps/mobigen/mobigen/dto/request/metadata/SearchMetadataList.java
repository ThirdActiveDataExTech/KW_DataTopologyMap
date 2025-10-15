package kware.apps.mobigen.mobigen.dto.request.metadata;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName     SearchMetadataList
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      필터링된 메타데이터 목록 전달
**/

@Getter @Setter
public class SearchMetadataList {

    private String action;              // "search"
    private Filters filters;
    private Pagination pagination;
    private Sort sort;


    @Getter @Setter
    public static class Filters {

        private String publisher;
        private List<String> theme;
        private DateRange date_range;

        @Getter @Setter
        public static class DateRange {
            private String start;
            private String end;
        }
    }

    @Getter @Setter
    public static class Pagination {
        private int page;
        private int limit;
    }

    @Getter @Setter
    public static class Sort {
        private String order;
    }
}
