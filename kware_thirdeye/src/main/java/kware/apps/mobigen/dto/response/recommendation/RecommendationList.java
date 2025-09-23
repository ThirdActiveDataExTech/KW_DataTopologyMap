package kware.apps.mobigen.dto.response.recommendation;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class RecommendationList {

    private String metadata_id;
    private List<Recommendations> recommendations;

    @Getter @Setter
    public static class Recommendations {
        private String metadata_id;
        private String title;
        private float recommendation_score;
        private String reason;
        private String recommendation_type;
    }
}
