package kware.apps.mobigen.integration.dto.response.recommendation;


import kware.apps.mobigen.mobigen.dto.response.recommendation.RecommendationsResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class RecommendationList {

    private String metadataId;

    private RecommendationsResponse recommendationResponse;

    public RecommendationList(String  metadataId) {
        this.metadataId = metadataId;
    }
}
