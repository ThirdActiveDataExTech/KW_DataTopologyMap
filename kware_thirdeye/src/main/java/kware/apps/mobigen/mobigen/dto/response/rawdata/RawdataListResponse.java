package kware.apps.mobigen.mobigen.dto.response.rawdata;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName     RawdataListResponse
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      [RAWDATA_01] 메타데이터에 등록된 전체 원본데이터 목록 조회 응답 DTO
**/

@Getter @Setter
public class RawdataListResponse {

    private String metadata_id;
    private int total_count;
    private List<RawdataListItemResponse> items;

    @Getter @Setter
    public static class RawdataListItemResponse {
        private String rawdata_id;
        private String filename;
        private int file_size;
        private String format;
        private String updated_at;
        private String checksum;
    }
}
