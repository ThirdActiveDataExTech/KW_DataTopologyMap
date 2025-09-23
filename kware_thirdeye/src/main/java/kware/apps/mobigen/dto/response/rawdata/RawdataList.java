package kware.apps.mobigen.dto.response.rawdata;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
