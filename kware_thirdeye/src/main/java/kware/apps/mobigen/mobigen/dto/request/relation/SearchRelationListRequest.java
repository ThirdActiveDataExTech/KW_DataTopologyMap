package kware.apps.mobigen.mobigen.dto.request.relation;


import kware.apps.mobigen.mobigen.dto.request.common.PaginationRequest;
import kware.apps.mobigen.mobigen.dto.request.common.SortFieldRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


/**
* @fileName     SearchRelationListRequest
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-15
* @summary      [RELATION_01] 연관 메타데이터 목록 조회 요청 DTO
**/

@Getter @Setter @ToString
public class SearchRelationListRequest {

    private String action;                          // "search"
    private SearchRelationListFilters filters;
    private PaginationRequest pagination;
    private SortFieldRequest sort;

    public SearchRelationListRequest( String publisher, List<String> theme,
                                      float min, float max,
                                      int page, int limit,
                                      String sortOrder, String sortField ) {
        this.action = "search";
        this.filters = new SearchRelationListFilters(publisher, theme, new SimilarityScore(min, max));
        this.pagination = new PaginationRequest(page, limit, false);
        this.sort = new SortFieldRequest(sortOrder, sortField);
    }
}
