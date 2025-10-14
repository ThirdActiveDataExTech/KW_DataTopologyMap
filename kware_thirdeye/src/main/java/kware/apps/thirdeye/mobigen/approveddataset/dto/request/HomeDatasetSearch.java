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
    private String selectedCategory;

    public HomeDatasetSearch(Long mainUiUid, Integer limitCount, String selectedCategory) {
        this.mainUiUid = mainUiUid;
        this.limitCount = limitCount;
        this.selectedCategory = selectedCategory;
    }
}
