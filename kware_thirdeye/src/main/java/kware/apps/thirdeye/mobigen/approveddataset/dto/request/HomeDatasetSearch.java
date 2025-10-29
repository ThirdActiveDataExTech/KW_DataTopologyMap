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
 *
 *              => {userUid} 값을 통해 각 데이터셋에 대해서 현재 로그인한 사용자의 북마크 여부 체크
**/

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HomeDatasetSearch {

    private Long mainUiUid;             // 데이터셋에 대한 화면_ui_uid
    private Integer limitCount;         // 각 type 별로 최대 보여지는 데이터셋 목록 개수
    private Long selectedCategory;      // 선택된 카테고리 유형
    private Long workplaceUid;          // 워크플레이스 uid
    private Long userUid;

    public HomeDatasetSearch( Long mainUiUid, Integer limitCount, Long selectedCategory,
                              Long workplaceUid, Long userUid ) {
        this.mainUiUid = mainUiUid;
        this.limitCount = limitCount;
        this.selectedCategory = selectedCategory;
        this.workplaceUid = workplaceUid;
        this.userUid = userUid;
    }

    public void setWorkplaceUid(Long workplaceUid) {
        this.workplaceUid = workplaceUid;
    }

    public void setUserUid(Long userUid) {
        this.userUid = userUid;
    }
}
