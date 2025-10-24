package kware.apps.mobigen.mobigen.dto.request.metadata;


import kware.apps.mobigen.mobigen.dto.request.common.PaginationRequest;
import kware.apps.mobigen.mobigen.dto.request.common.SortRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName     SearchMetadataListRequest
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      [METADATA_01] 필터링된 메타데이터 목록 요청 DTO
**/

@Getter @Setter
public class SearchMetadataListRequest {

    private String action;              // "search"
    private Filters filters;
    private PaginationRequest pagination;
    private SortRequest sort;

    public SearchMetadataListRequest(String publisher, List<String> theme, int page, int limit, String start, String end, String sortOrder) {
        this.action = "search";
        this.filters = new Filters(publisher, theme, new Filters.DateRange(start, end));
        this.pagination = new PaginationRequest(page, limit);
        this.sort = new SortRequest(sortOrder);
    }

    @Getter @Setter
    public static class Filters {

        private String publisher;
        private List<String> theme;
        private DateRange date_range;

        public Filters(String publisher, List<String> theme, DateRange date_range) {
            this.publisher = publisher;
            this.theme = theme;
            this.date_range = date_range;
        }

        @Getter @Setter
        public static class DateRange {
            private String start;
            private String end;

            public DateRange(String start, String end) {
                this.start = start;
                this.end = end;
            }
        }
    }
}
