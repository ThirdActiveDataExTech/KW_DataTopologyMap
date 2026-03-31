package kware.apps.mobigen.mobigen.dto.response.rawdata;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class RawdataListItemResponse {
    private String rawdata_id;
    private String filename;
    private String file_size;
    private String format;
    private String uploaded_at;
}
