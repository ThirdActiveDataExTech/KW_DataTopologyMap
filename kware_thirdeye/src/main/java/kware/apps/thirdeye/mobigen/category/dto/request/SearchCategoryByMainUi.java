package kware.apps.thirdeye.mobigen.category.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchCategoryByMainUi {

    private Long mainUiUid;

    public SearchCategoryByMainUi(Long mainUiUid) {
        this.mainUiUid = mainUiUid;
    }
}
