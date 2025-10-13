package kware.apps.thirdeye.mobigen.approveddataset.dto.response;

import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetView;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DatasetList {

    private Long approvedUid;       // cetus_approved_dataset.uid (pk)
    private Long datasetId;         // 데이터셋 ID
    private String approvedDt;      // 승인 일시
    private String createdNm;       // 데이터 등록자 이름
    private String approverNm;      // 데이터 승인자 이름
    private Integer ratings;        // 데이터셋 의견/평점

    private Long mainUiUid;         // 메인 UI uid
    private String mainUiCode;      // 메인 UI 코드
    private String mainUiTypeCd;    // 메인 UI 타입
    private String mainUiTypeCdDescription; // 메인 UI 타입 설명
    private String showAt;          // 데이터셋 노출 여부

    private String thumbId;
    private String bookmarkYn;
    private String extraJson;

    private MobigenDatasetView datasetView;

    public void setDatasetInfo(MobigenDatasetView datasetView) {
        this.datasetView = datasetView;
    }

    public void setMainUiTypeCdDescription(String mainUiTypeCdDescription) {
        this.mainUiTypeCdDescription = mainUiTypeCdDescription;
    }
}
