package kware.apps.mobigen.mobigen.dto.response.rawdata;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName     ViewRawdataResponse
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      [RAWDATA_04] 특정 원본데이터 상세 정보 조회 응답 DTO
**/

@Getter @Setter
public class ViewRawdataResponse {
    private String metadata_id;
    private String rawdata_id;
    private String filename;
    private int file_size;
    private String format;
    private String mime_type;
    private String uploaded_at;
    private String checksum;
    private String description;
    private List<String> tags;
    private int download_count;
}
