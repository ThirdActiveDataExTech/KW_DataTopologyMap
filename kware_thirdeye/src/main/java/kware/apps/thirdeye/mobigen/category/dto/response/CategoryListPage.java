package kware.apps.thirdeye.mobigen.category.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryListPage {

    private Long categoryUid;
    private String categoryNm;
    private String regDt;
    private String regNm;
    private Long regUid;
    private Integer useCnt;
}
