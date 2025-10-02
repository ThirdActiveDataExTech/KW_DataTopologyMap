package kware.apps.thirdeye.approveddataset.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED) @Setter
public class ApprovedDatasetSearch {

    private Long workplaceUid;
    private String showAt;
    private String browseText;
    private String startDate;
    private String endDate;

    public ApprovedDatasetSearch(Long workplaceUid, String showAt, String browseText, String startDate, String endDate) {
        this.workplaceUid = workplaceUid;
        this.showAt = showAt;
        this.browseText = browseText;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setWorkplaceUid(Long workplaceUid) {
        this.workplaceUid = workplaceUid;
    }
}
