package kware.apps.thirdeye.mobigen.approveddataset.dto.response;

import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetView;
import kware.apps.mobigen.integration.dto.response.metadata.MetadataView;
import kware.apps.thirdeye.mobigen.datasetui.dto.response.DatasetUiView;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HomeDatasetList {

    private Long approvedUid;
    private Long datasetId;

    private Long categoryUid;
    private String categoryNm;

    private Long thumbUid;
    private String thumbId;

    private String targetTpCd;      // 원본 데이터셋 저장 위치
    private String targetTpCdNm;    // 원본 데이터셋 저장 위치 설명

    private DatasetUiView uiView;
    private MetadataView metadataView;

    private Integer ratings;
    private String bookmarkYn;

    private String approvedDt;
    private String approverNm;

    public void setUiView(DatasetUiView uiView) {
        this.uiView = uiView;
    }
    public void setMetadataView(MetadataView metadataView){
        this.metadataView = metadataView;
    }
    public void setTargetTpCdNm(String targetTpCdNm) {
        this.targetTpCdNm = targetTpCdNm;
    }
}
