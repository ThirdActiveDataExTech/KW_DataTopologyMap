package kware.apps.thirdeye.datasetui.domain;


import cetus.bean.AuditBean;
import kware.apps.thirdeye.approveddataset.dto.request.SaveApprovedDataset;
import kware.apps.thirdeye.datasetui.dto.request.ChangeDatasetUi;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusDatasetUi extends AuditBean {

    private Long uid;
    private Long approvedDatasetUid;
    private Long sortNo;
    private Long mainUiUid;
    private String showAt;
    private Long thumbUid;

    public CetusDatasetUi(Long approvedDatasetUid, Long thumbUid, SaveApprovedDataset request) {
        this.approvedDatasetUid = approvedDatasetUid;
        this.thumbUid = thumbUid;
        this.sortNo = request.getSortNo();
        this.mainUiUid = request.getMainUiUid();
        this.showAt = request.getShowAt();
    }

    public CetusDatasetUi(Long uid, ChangeDatasetUi request, Long thumbUid) {
        this.uid = uid;
        this.sortNo = request.getSortNo();
        this.mainUiUid = request.getMainUiUid();
        this.showAt = request.getShowAt();
        this.thumbUid = thumbUid;
    }

}
