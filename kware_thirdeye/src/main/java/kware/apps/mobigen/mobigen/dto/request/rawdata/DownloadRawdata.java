package kware.apps.mobigen.mobigen.dto.request.rawdata;


import lombok.Getter;
import lombok.Setter;

/**
* @fileName     DownloadRawdata
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-15
* @summary      특정 원본데이터 다운로드
**/

@Getter @Setter
public class DownloadRawdata {

    private String metadata_id;
    private String rawdata_id;
}
