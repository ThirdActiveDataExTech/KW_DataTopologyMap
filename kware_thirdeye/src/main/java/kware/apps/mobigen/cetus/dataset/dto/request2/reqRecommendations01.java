package kware.apps.mobigen.cetus.dataset.dto.request2;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED) @ToString
public class reqRecommendations01 {

    private final String action = "search";
    private reqRecommendations01_filters filters;
    private reqRecommendations01_pagination pagination;
    private reqRecommendations01_sort sort;

    @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED) @ToString
    public static class reqRecommendations01_filters {
        private List<String> recommendation_type;
        private String publisher;
        private List<String> theme;
    }

    @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED) @ToString
    public static class reqRecommendations01_pagination {
        private int page;
        private int limit;
    }

    @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED) @ToString
    public static class reqRecommendations01_sort {
        private String field;
        private String order;
    }
}
