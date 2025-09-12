package kware.apps.thirdeye.dataset.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DatasetSearch {

    private String startDate;
    private String endDate;
    private Long workplaceUid;

    public DatasetSearch(String startDate, String endDate, Long workplaceUid) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.workplaceUid = workplaceUid;
    }

    public void setWorkplaceUid(Long workplaceUid) {
        this.workplaceUid = workplaceUid;
    }
}
