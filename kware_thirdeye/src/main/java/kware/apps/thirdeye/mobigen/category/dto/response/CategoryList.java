package kware.apps.thirdeye.mobigen.category.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryList {

    private Long categoryUid;
    private String categoryNm;
}
