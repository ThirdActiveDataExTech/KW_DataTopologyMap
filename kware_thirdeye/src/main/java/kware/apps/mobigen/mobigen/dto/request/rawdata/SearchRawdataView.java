package kware.apps.mobigen.mobigen.dto.request.rawdata;


import lombok.Getter;
import lombok.Setter;

/**
* @fileName     SearchRawdataView
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-15
* @summary      특정 원본데이터 상세 정보 조회
**/

@Getter @Setter
public class SearchRawdataView {

    private String action;          // "detail"
    private String metadata_id;
    private String rawdata_id;
}
