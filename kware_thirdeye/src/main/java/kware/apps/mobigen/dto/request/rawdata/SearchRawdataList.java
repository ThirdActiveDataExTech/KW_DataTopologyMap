package kware.apps.mobigen.dto.request.rawdata;


import lombok.Getter;
import lombok.Setter;

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
