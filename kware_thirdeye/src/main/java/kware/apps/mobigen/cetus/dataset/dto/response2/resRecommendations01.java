package kware.apps.mobigen.cetus.dataset.dto.response2;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class resRecommendations01 {

    private String code;
    private String message;
    private resRecommendations01_result result;

    public resRecommendations01(String code, String message, resRecommendations01_result result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class resRecommendations01_result {
        private String metadata_id;
        private int total_count;
        private int page;
        private int limit;
        private List<resRecommendations01_items> items;

        public resRecommendations01_result(String metadata_id, int total_count, int page, int limit, List<resRecommendations01_items> items) {
            this.metadata_id = metadata_id;
            this.total_count = total_count;
            this.page = page;
            this.limit = limit;
            this.items = items;
        }

        @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class resRecommendations01_items {
            private String metadata_id;
            private String title;
            private float recommendation_score;
            private String reason;
            private String recommendation_type;

            public resRecommendations01_items(String metadata_id, String title, float recommendation_score, String reason, String recommendation_type) {
                this.metadata_id = metadata_id;
                this.title = title;
                this.recommendation_score = recommendation_score;
                this.reason = reason;
                this.recommendation_type = recommendation_type;
            }
        }

    }
}
