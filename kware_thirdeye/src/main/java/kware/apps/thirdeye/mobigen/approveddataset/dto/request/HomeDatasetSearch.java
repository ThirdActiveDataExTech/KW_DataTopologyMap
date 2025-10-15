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

    private Long mainUiUid;             // 데이터셋에 대한 화면_ui_uid
    private Integer limitCount;         // 각 type 별로 최대 보여지는 데이터셋 목록 개수
    private Long selectedCategory;      // 선택된 카테고리 유형
    private Long workplaceUid;          // 워크플레이스 uid

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
