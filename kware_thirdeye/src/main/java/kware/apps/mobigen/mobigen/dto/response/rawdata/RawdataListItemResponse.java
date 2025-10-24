package kware.apps.mobigen.mobigen.dto.response.rawdata;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RawdataListItemResponse {
    private String rawdataId;
    private String rawdata_id;
    private String filename;
    private int file_size;
    private String format;
    private String updated_at;
    private String checksum;
}
