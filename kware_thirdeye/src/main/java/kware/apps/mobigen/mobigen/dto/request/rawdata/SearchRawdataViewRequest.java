package kware.apps.mobigen.mobigen.dto.request.rawdata;


import lombok.Getter;
import lombok.Setter;

/**
* @fileName     SearchRawdataViewRequest
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-15
* @summary      [RAWDATA_04] 특정 원본데이터 상세 정보 조회 요청 DTO
**/

@Getter @Setter
public class SearchRawdataViewRequest {
    private String action;          // "detail"
    private String metadata_id;
    private String rawdata_id;
}
