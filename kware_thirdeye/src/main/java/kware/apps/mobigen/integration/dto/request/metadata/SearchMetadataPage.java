package kware.apps.mobigen.integration.dto.request.metadata;

import kware.apps.mobigen.mobigen.dto.request.common.PaginationRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class SearchMetadataPage {

    private String publisher;
    private List<String> theme;
    private String dateRangeStart;
    private String dateRangeEnd;

    // kware table.js 에서는 { page -> pageNumber } { limit -> size } 를 이용
    private int pageNumber;
    private int size;

    private String sortOrder = "desc";

}
