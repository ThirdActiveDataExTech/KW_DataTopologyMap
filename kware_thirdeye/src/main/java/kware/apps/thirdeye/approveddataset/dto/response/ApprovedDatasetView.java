package kware.apps.thirdeye.approveddataset.dto.response;


import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetView;
import kware.apps.thirdeye.datasetui.dto.response.DatasetUiView;
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
    private String createdId;

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
}
