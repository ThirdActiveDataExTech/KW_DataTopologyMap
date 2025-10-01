package kware.apps.thirdeye.datasetuihistory.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusDatasetHistory {

    private Long historyUid;
    private Long approvedDatasetUid;
    private String chngCnt;
    private String chngDt;
    private Long chngUid;

    public CetusDatasetHistory(Long approvedDatasetUid, String chngCnt, Long chngUid) {
        this.approvedDatasetUid = approvedDatasetUid;
        this.chngCnt = chngCnt;
        this.chngUid = chngUid;
    }
}
