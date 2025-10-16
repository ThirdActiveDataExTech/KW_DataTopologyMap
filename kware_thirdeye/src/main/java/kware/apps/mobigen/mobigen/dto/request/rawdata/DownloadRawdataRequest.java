package kware.apps.mobigen.mobigen.dto.request.rawdata;


import lombok.Getter;
import lombok.Setter;

/**
* @fileName     DownloadRawdataRequest
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-15
* @summary      [RAWDATA_07] 특정 원본데이터 다운로드 요청 DTO
**/

@Getter @Setter
public class DownloadRawdataRequest {
    private String metadata_id;
    private String rawdata_id;
}
