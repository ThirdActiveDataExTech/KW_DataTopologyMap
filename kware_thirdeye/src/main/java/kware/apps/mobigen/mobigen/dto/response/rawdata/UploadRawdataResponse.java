package kware.apps.mobigen.mobigen.dto.response.rawdata;


import lombok.Getter;
import lombok.Setter;

/**
* @fileName     UploadRawdataResponse
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      [RAWDATA_02] 메타데이터 하위 원본데이터 추가 응답 DTO
**/

@Getter @Setter
public class UploadRawdataResponse {
    private String rawdata_id;
    private String filename;
    private int file_size;
    private String updated_at;
}
