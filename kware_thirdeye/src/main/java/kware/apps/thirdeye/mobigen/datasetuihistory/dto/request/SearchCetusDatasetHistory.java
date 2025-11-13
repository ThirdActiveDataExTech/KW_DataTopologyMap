package kware.apps.thirdeye.mobigen.datasetuihistory.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchCetusDatasetHistory {
    private Long approvedDatasetUid;

    public SearchCetusDatasetHistory(Long approvedDatasetUid) {
        this.approvedDatasetUid = approvedDatasetUid;
    }
}
