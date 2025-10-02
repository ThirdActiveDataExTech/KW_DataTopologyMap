package kware.apps.thirdeye.approveddataset.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED) @Setter
public class ApprovedDatasetSearch {

    private String startDate;
    private String endDate;
    private Long workplaceUid;
    private Long[] categories;
    private Long[] tags;

    public ApprovedDatasetSearch(String startDate, String endDate, Long workplaceUid, Long[] categories, Long[] tags) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.workplaceUid = workplaceUid;
        this.categories = categories;
        this.tags = tags;
    }

    public void setWorkplaceUid(Long workplaceUid) {
        this.workplaceUid = workplaceUid;
    }
}
