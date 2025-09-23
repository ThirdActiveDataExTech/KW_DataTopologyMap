package kware.apps.mobigen.dto.response.recommendation;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName     RecommendationList
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      메타데이터의 추천 데이터 조회
**/

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
