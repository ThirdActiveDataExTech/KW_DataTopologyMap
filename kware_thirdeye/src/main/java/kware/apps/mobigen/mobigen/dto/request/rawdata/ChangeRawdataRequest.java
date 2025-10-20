package kware.apps.mobigen.mobigen.dto.request.rawdata;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName     ChangeRawdataRequest
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      [RAWDATA_05] 원본데이터의 상세정보 수정 요청 DTO
**/

@Getter @Setter
public class ChangeRawdataRequest {

    private String action;                      // "update"
    private String metadata_id;
    private ChangeRawdataFieldRequest field;

    @Getter @Setter
    public static class ChangeRawdataFieldRequest {
        private String description;
        private List<String> tags;
    }
}
