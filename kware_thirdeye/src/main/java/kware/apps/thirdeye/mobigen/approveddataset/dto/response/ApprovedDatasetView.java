package kware.apps.thirdeye.mobigen.approveddataset.dto.response;


import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetView;
import kware.apps.thirdeye.mobigen.datasetui.dto.response.DatasetUiView;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApprovedDatasetView {

    private Long approvedUid;
    private Long datasetId;
    private Long workplaceUid;
    private String approvedDt;
    private Long approverUid;
    private String targetTpCd;      // 원본 데이터셋 저장 위치
    private String targetTpCdNm;    // 원본 데이터셋 저장 위치 설명

    private String deleteAt;

    private Long categoryUid;
    private String categoryNm;

    private DatasetUiView uiView;
    private MobigenDatasetView mobigenDatasetView;

    private String thumbId;
    private Integer ratings;
    private String bookmarkYn;
    private String extraJson;

    public void setUiView(DatasetUiView uiView) {
        this.uiView = uiView;
    }

    public void setMobigenDatasetView(MobigenDatasetView mobigenDatasetView) {
        this.mobigenDatasetView = mobigenDatasetView;
    }

    public void setTargetTpCdNm(String targetTpCdNm) {
        this.targetTpCdNm = targetTpCdNm;
    }
}
