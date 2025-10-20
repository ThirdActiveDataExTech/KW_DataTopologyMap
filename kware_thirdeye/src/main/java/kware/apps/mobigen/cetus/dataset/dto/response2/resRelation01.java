package kware.apps.mobigen.cetus.dataset.dto.response2;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class resRelation01 {
    private String code;
    private String message;
    private resRelation01_result result;

    public resRelation01(String code, String message, resRelation01_result result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class resRelation01_result {
        private String metadata_id;
        private int total_count;
        private int page;
        private int limit;
        private List<resRelation01_items> items;
        private List<resRelation01_relations> field_relations;

        public resRelation01_result(String metadata_id, int total_count, int page, int limit, List<resRelation01_items> items, List<resRelation01_relations> field_relations) {
            this.metadata_id = metadata_id;
            this.total_count = total_count;
            this.page = page;
            this.limit = limit;
            this.items = items;
            this.field_relations = field_relations;
        }

        @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class resRelation01_items {
            private String metadata_id;
            private String title;
            private float similarity_score;
            private List<String> common_fields;

            public resRelation01_items(String metadata_id , String title, float similarity_score, List<String> common_fields) {
                this.metadata_id = metadata_id;
                this.title = title;
                this.similarity_score = similarity_score;
                this.common_fields = common_fields;
            }
        }

        @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class resRelation01_relations {
            private String source_field;
            private String target_field;
            private float correlation;

            public resRelation01_relations(String source_field, String target_field, float correlation) {
                this.source_field = source_field;
                this.target_field = target_field;
                this.correlation = correlation;
            }
        }
    }
}
