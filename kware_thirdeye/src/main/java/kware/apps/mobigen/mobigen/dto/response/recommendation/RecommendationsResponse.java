package kware.apps.mobigen.mobigen.dto.response.recommendation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class RecommendationsResponse {
    private String metadata_id;
    private String title;
    private float recommendation_score;
    private String reason;
    private String recommendation_type;
}
