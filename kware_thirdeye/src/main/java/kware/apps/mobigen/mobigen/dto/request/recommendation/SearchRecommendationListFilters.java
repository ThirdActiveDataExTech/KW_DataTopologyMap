package kware.apps.mobigen.mobigen.dto.request.recommendation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter @ToString
public class SearchRecommendationListFilters {
    private List<String> recommendation_type;
    private String publisher;
    private List<String> theme;

    public SearchRecommendationListFilters(List<String> recommendation_type, String publisher, List<String> theme) {
        this.recommendation_type = recommendation_type;
        this.publisher = publisher;
        this.theme = theme;
    }
}
