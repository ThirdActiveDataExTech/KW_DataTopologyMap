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
 *              => workplaceUid, browseText, startDate&endDate
 *
 *              (3) 연관/관련 데이터셋 목록 검색
 *              => workplaceUid, browseText
**/

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED) @Setter
public class ApprovedDatasetSearch {

    private Long workplaceUid;
    private String showAt;
    private String browseText;
    private String typeCd;
    private String startDate;
    private String endDate;
    private List<Long> exceptUids;  // 제외할 데이터셋ID 목록

    public ApprovedDatasetSearch(Long workplaceUid, String showAt, String browseText, String typeCd, String startDate, String endDate, List<Long> exceptUids) {
        this.workplaceUid = workplaceUid;
        this.showAt = showAt;
        this.browseText = browseText;
        this.typeCd = typeCd;
        this.startDate = startDate;
        this.endDate = endDate;
        this.exceptUids = exceptUids;
    }

    public void setWorkplaceUid(Long workplaceUid) {
        this.workplaceUid = workplaceUid;
    }
}
