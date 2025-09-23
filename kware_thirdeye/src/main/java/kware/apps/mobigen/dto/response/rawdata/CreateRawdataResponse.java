package kware.apps.mobigen.dto.response.rawdata;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateRawdataResponse {

    private String rawdata_id;
    private String filename;
    private int file_size;
    private String updated_at;

}
