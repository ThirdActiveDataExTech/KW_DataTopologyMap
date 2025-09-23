package kware.apps.mobigen.dto.request.rawdata;


import lombok.Getter;
import lombok.Setter;

/**
* @fileName     SearchRawdataList
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      메타데이터에 등록된 전체 원본데이터 목록 조회
**/

@Getter @Setter
public class SearchRawdataList {

    private String format;
    private Pagination pagination;

    @Getter @Setter
    public static class Pagination {
        private int page;
        private int limit;
    }
}
