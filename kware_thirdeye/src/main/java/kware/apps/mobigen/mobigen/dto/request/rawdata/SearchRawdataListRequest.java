package kware.apps.mobigen.mobigen.dto.request.rawdata;


import kware.apps.mobigen.mobigen.dto.request.common.PaginationRequest;
import lombok.Getter;
import lombok.Setter;

/**
* @fileName     SearchRawdataListRequest
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      [RAWDATA_01] 메타데이터에 등록된 전체 원본데이터 목록 조회 요청 DTO
**/

@Getter @Setter
public class SearchRawdataListRequest {

    private String action;                  // "list"
    private String metadata_id;
    private PaginationRequest pagination;
}
