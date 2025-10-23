package kware.apps.thirdeye.mobigen.approveddataset.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
* @fileName     ApprovedDatasetSearch
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-14
* @summary      (1) 승인된 데이터셋 관리 (system)
 *              => workplaceUid, showAt, typeCd
 *
 *              (2) 메인 리스트 화면 데이터셋 검색
 *              => workplaceUid, browseText, startDate&endDate, categories
 *
 *              (3) 연관/관련 데이터셋 목록 검색
 *              => workplaceUid, browseText
**/

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED) @Setter
public class ApprovedDatasetSearch {

    private Long workplaceUid;
    private String showAt;
    private String browseText;
    private String uiTypeCd;
    private String dataTarType;
    private String startDate;
    private String endDate;
    private List<Long> categories;  // 카테고리 검색

    public ApprovedDatasetSearch( Long workplaceUid, String showAt, String browseText, String uiTypeCd, String dataTarType,
                                  String startDate, String endDate, List<Long> categories ) {
        this.workplaceUid = workplaceUid;
        this.showAt = showAt;
        this.browseText = browseText;
        this.uiTypeCd = uiTypeCd;
        this.dataTarType = dataTarType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.categories = categories;
    }

    public void setWorkplaceUid(Long workplaceUid) {
        this.workplaceUid = workplaceUid;
    }
}
