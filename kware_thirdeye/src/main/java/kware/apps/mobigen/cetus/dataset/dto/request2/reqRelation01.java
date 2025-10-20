package kware.apps.mobigen.cetus.dataset.dto.request2;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED) @ToString
public class reqRelation01 {

    private final String action = "search";
    private reqRelation01_filters filters;
    private reqRelation01_pagination pagination;
    private reqRelation01_sort sort;

    @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED) @ToString
    public static class reqRelation01_filters {
        private String publisher;
        public List<String> theme;
        public reqRelation01_similarity similarity_score;

        @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED) @ToString
        public static class reqRelation01_similarity {
            private float min;
            private float max;
        }
    }

    @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED) @ToString
    public static class reqRelation01_pagination {
        private int page;
        private int limit;
    }

    @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED) @ToString
    public static class reqRelation01_sort {
        private String field;
        private String order;
    }
}
