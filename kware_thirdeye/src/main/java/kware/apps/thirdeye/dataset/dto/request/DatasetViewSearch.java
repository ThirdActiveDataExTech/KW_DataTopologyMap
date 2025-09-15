package kware.apps.thirdeye.dataset.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DatasetViewSearch {

    private Long approvedUid;
    private Long userUid;

    public DatasetViewSearch(Long approvedUid, Long userUid) {
        this.approvedUid = approvedUid;
        this.userUid = userUid;
    }
}
