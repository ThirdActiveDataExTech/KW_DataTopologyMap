package kware.apps.mobigen.cetus.category.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchCategory {

    private String categoryNm;

    public SearchCategory(String categoryNm) {
        this.categoryNm = categoryNm;
    }
}
