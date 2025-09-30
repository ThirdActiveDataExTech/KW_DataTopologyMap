package kware.apps.mobigen.mobigen.dto.response.rawdata;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName     ViewRawdata
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      원본데이터의 상세 메타정보 조회
**/

@Getter @Setter
public class ViewRawdata {

    private String rawdata_id;
    private String metadata_id;
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
