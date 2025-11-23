package kware.apps.mobigen.integration.dto.request.recommendation;


import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter @Setter
public class SearchRecommendationPage {

    private List<String> recommendationType = Arrays.asList("content_based", "collaborative");
    private String publisher;
    private List<String> theme = Arrays.asList("보건", "의료");

    // kware table.js 에서는 { page -> pageNumber } { limit -> size } 를 이용
    private int pageNumber;
    private int size;

    private String sortOrder = "desc";
    private String sortField = "recommendation_score";
}
