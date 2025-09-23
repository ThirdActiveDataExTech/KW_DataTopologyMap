package kware.apps.mobigen.dto.response.rawdata;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName     RawdataList
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      메타데이터에 등록된 전체 원본데이터 목록 조회
**/

@Getter @Setter
public class RawdataList {

    private String metadata_id;
    private int total_count;
    private List<Items> items;

    @Getter @Setter
    public static class Items {
        private String rawdata_id;
        private String filename;
        private int file_size;
        private String format;
        private String updated_at;
        private String checksum;
    }
}
