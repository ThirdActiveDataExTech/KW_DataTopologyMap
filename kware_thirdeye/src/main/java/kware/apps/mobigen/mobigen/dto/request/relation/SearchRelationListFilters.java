package kware.apps.mobigen.mobigen.dto.request.relation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter @Setter @ToString
public class SearchRelationListFilters {
    private String publisher;
    private List<String> theme;
    private SimilarityScore similarity_score;

    public SearchRelationListFilters(String publisher, List<String> theme, SimilarityScore similarity_score) {
        this.publisher = publisher;
        this.theme = theme;
        this.similarity_score = similarity_score;
    }
}
