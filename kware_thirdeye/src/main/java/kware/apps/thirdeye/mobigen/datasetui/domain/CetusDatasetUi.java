package kware.apps.thirdeye.mobigen.datasetui.domain;


import cetus.bean.AuditBean;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.SaveApprovedDataset;
import kware.apps.thirdeye.mobigen.datasetui.dto.request.ChangeDatasetUi;
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
    private String extraJson;

    public CetusDatasetUi(Long approvedDatasetUid, Long thumbUid, SaveApprovedDataset request) {
        this.approvedDatasetUid = approvedDatasetUid;
        this.thumbUid = thumbUid;
        this.sortNo = request.getSortNo();
        this.mainUiUid = request.getMainUiUid();
        this.showAt = request.getShowAt();
        this.extraJson = request.getExtraJson();
    }

    public CetusDatasetUi(Long uid, ChangeDatasetUi request, Long thumbUid) {
        this.uid = uid;
        this.sortNo = request.getSortNo();
        this.mainUiUid = request.getMainUiUid();
        this.showAt = request.getShowAt();
        this.thumbUid = thumbUid;
        this.extraJson = request.getExtraJson();
    }

}
