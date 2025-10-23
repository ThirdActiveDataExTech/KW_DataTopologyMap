package kware.apps.mobigen.mobigen.dto.request.rawdata;

import lombok.Getter;
import lombok.Setter;

/**
* @fileName     UploadRawdataRequest
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-15
* @summary      [RAWDATA_02] 메타데이터 하위 원본데이터 추가 요청 DTO
**/

@Getter @Setter
public class UploadRawdataRequest {
    private String action;              // "create"
    private String metadata_id;
    private String rawdata_format;

    public UploadRawdataRequest(String metadata_id, String rawdata_format) {
        this.action = "create";
        this.metadata_id = metadata_id;
        this.rawdata_format = rawdata_format;
    }
}
