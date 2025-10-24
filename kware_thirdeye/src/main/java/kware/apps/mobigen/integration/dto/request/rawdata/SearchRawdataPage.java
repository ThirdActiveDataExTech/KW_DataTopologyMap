package kware.apps.mobigen.integration.dto.request.rawdata;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SearchRawdataPage {

    private String metadataId;

    // kware table.js 에서는 { page -> pageNumber } { limit -> size } 를 이용
    private int pageNumber;
    private int size;
}
