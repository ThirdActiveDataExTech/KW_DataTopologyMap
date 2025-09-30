package kware.apps.mobigen.mobigen.dto.response.rawdata;


import lombok.Getter;
import lombok.Setter;

/**
* @fileName     DownloadRawdataResponse
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      원본파일 다운로드
**/

@Getter @Setter
public class DownloadRawdataResponse {

    private String metadata_id;
    private String rawdata_id;
}
