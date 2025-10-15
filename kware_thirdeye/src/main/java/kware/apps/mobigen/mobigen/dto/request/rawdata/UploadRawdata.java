package kware.apps.mobigen.mobigen.dto.request.rawdata;

import lombok.Getter;
import lombok.Setter;

/**
* @fileName     UploadRawdata
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-15
* @summary      메타데이터 하위 원본데이터 추가
**/

@Getter @Setter
public class UploadRawdata {

    private String action;
    private String metadata_id;
    private String rawdata_format;
}
