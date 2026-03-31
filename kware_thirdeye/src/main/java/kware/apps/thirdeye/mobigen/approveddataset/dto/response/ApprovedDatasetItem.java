package kware.apps.thirdeye.mobigen.approveddataset.dto.response;

import kware.apps.mobigen.integration.dto.response.metadata.MetadataView;
import kware.apps.thirdeye.mobigen.datasetui.dto.response.DatasetUiView;
import kware.apps.thirdeye.mobigen.mainui.dto.response.MainUiView;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApprovedDatasetItem {
    
    private Long approvedUid;       // cetus_approved_dataset.uid (pk)
    private String metadataId;      // 데이터셋 ID
    private String approvedDt;      // 승인 일시
    private String approverNm;      // 데이터 승인자 이름
    private String targetTpCd;      // 원본 데이터셋 저장 위치
    private String targetTpCdNm;    // 원본 데이터셋 저장 위치 설명
    private Integer ratings;        // 데이터셋 의견/평점
    private String bookmarkYn;      // 북마크 여부
    private String deleteAt;        // 진열 등록 삭제 여부

    private MetadataView metadataView;  // 메타데이터 정보
    public void setMetadataView(MetadataView metadataView) {
        this.metadataView = metadataView;
    }

    private DatasetUiView datasetUi;
    public void setDatasetUi(DatasetUiView datasetUi) {
        this.datasetUi = datasetUi;
    }

    private MainUiView mainUi;
    public void setMainUi(MainUiView mainUi) {
        this.mainUi = mainUi;
    }
}
