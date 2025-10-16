package kware.apps.mobigen.mobigen.dto.response.recommendation;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName     RecommendationListResponse
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      [RECOMMENDATION_01] 추천 메타데이터 목록 조회 응답 DTO
**/

@Getter @Setter
public class RecommendationListResponse {

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
