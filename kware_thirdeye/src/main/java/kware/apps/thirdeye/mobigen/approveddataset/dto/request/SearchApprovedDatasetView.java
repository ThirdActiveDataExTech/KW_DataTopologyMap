package kware.apps.thirdeye.mobigen.approveddataset.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchApprovedDatasetView {

    private Long approvedUid;
    private Long userUid;

    public SearchApprovedDatasetView(Long approvedUid, Long userUid) {
        this.approvedUid = approvedUid;
        this.userUid = userUid;
    }
}
