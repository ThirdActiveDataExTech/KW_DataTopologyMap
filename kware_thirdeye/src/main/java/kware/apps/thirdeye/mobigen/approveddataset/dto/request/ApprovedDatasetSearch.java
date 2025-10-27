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
* @summary
**/

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED) @Setter
public class ApprovedDatasetSearch {

    private Long workplaceUid;
    private Long userUid;
    private String showAt;
    private String uiTypeCd;
    private String dataTarType;
    private String startDate;
    private String endDate;
    private String keyword;
    private List<Long> categories;  // 카테고리 검색
    private List<Long> tags;        // tag 검색

    public ApprovedDatasetSearch( Long workplaceUid, Long userUid,
                                  String showAt, String uiTypeCd, String dataTarType,
                                  String startDate, String endDate,
                                  String keyword, List<Long> categories, List<Long> tags ) {
        this.workplaceUid = workplaceUid;
        this.userUid = userUid;
        this.showAt = showAt;
        this.uiTypeCd = uiTypeCd;
        this.dataTarType = dataTarType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.keyword = keyword;
        this.categories = categories;
        this.tags = tags;
    }

    public void setWorkplaceUid(Long workplaceUid) {
        this.workplaceUid = workplaceUid;
    }
    public void setUserUid(Long userUid) {
        this.userUid = userUid;
    }
}
