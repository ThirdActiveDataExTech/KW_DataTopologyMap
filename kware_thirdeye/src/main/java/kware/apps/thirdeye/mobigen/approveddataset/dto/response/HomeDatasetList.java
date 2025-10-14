package kware.apps.thirdeye.mobigen.approveddataset.dto.response;

import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetView;
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

    private DatasetUiView uiView;
    private MobigenDatasetView mobigenDatasetView;

    private Integer ratings;
    private String bookmarkYn;

    public void setUiView(DatasetUiView uiView) {
        this.uiView = uiView;
    }
    public void setMobigenDatasetView(MobigenDatasetView mobigenDatasetView){
        this.mobigenDatasetView = mobigenDatasetView;
    }
}
