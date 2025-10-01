package kware.apps.thirdeye.approveddataset.dto.response;


import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetView;
import kware.apps.thirdeye.datasetui.dto.response.DatasetUiView;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApprovedDatasetView {

    private Long uid;
    private Long datasetId;
    private Long workplaceUid;
    private String approvedDt;
    private Long approvedUid;
    private String createdId;
    private DatasetUiView uiView;
    private MobigenDatasetView mobigenDatasetView;

    public void setUiView(DatasetUiView uiView) {
        this.uiView = uiView;
    }

    public void setMobigenDatasetView(MobigenDatasetView mobigenDatasetView) {
        this.mobigenDatasetView = mobigenDatasetView;
    }
}
