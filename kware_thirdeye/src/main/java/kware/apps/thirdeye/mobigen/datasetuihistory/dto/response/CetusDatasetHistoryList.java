package kware.apps.thirdeye.mobigen.datasetuihistory.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusDatasetHistoryList {
    private Long historyUid;
    private Long approvedDatasetUid;
    private String chngCnt;
    private String mainUiNm;
    private Long mainUiUid;
    private String categoryNm;
    private Long categoryUid;
    private String chngDt;
    private Long chngUid;
    private String chngNm;
}
