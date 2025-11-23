package kware.apps.mobigen.integration.dto.request.relation;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class SearchRelationsPage {

    // kware table.js 에서는 { page -> pageNumber } { limit -> size } 를 이용
    private int pageNumber;
    private int size;

    private String publisher;
    private List<String> theme;
    private float scoreMin = 0.0F;
    private float scoreMax = 1.0F;

    private String sortOrder = "desc";
    private String sortField = "similarity_score";
}
