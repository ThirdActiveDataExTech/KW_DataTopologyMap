package kware.apps.mobigen.dto.request.metadata;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class SearchMetadataList {

    private String action;
    private Filters filters;
    private Pagination pagination;

    @Getter @Setter
    public static class Filters {

        private String publisher;
        private List<String> theme;
        private DateRange date_range;
        private Sort sort;

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
