package kware.apps.thirdeye.mobigen.approveddataset.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
* @fileName     HomeDatasetSearch
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-14
* @summary      메인 홈 화면 데이터셋 리스트(목록) 조회를 위한 요청 dto
**/

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HomeDatasetSearch {

    private Long mainUiUid;
    private Integer limitCount;
    private Long selectedCategory;
    private Long workplaceUid;

    public HomeDatasetSearch(Long mainUiUid, Integer limitCount, Long selectedCategory, Long workplaceUid) {
        this.mainUiUid = mainUiUid;
        this.limitCount = limitCount;
        this.selectedCategory = selectedCategory;
        this.workplaceUid = workplaceUid;
    }

    public void setWorkplaceUid(Long workplaceUid) {
        this.workplaceUid = workplaceUid;
    }
}
