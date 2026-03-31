package kware.apps.mobigen.mobigen.dto.request.metadata.search;


import kware.apps.mobigen.mobigen.dto.request.common.PaginationRequest;
import kware.apps.mobigen.mobigen.dto.request.common.SortRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
* @fileName     SearchMetadataListRequest
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      [METADATA_01] 필터링된 메타데이터 목록 요청 DTO
**/

@Getter @Setter @ToString
public class SearchMetadataListRequest {

    private String action;              // "search"
    private SearchFilters filters;
    private PaginationRequest pagination;
    private SortRequest sort;

    public SearchMetadataListRequest( String publisher, List<String> keyword, List<String> theme,
                                      int page, int limit,
                                      String start, String end,
                                      String sortField, String sortOrder) {
        this.action = "search";
        this.filters = new SearchFilters(publisher, keyword, theme, new SearchDataRange(start, end));
        this.pagination = new PaginationRequest(page, limit, true);
        this.sort = new SortRequest(sortField, sortOrder);
    }
}
