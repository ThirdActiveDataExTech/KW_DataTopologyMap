package kware.apps.thirdeye.approveddataset.domain;


import kware.apps.thirdeye.approveddataset.dto.request.SaveDataset;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusDataset {

    private Long uid;
    private Long datasetId;         // 데이터셋 id
    private Long workplaceUid;      // 워크플레이스 uid
    private String approvedDt;      // 승인일시
    private Long approvedUid;       // 승인자 uid
    private String createdId;        // 데이터셋 등록자 id
    private String deleteAt;        // 삭제 여부

    public CetusDataset(SaveDataset request, Long workplaceUid, Long userUid) {
        this.datasetId = request.getDatasetId();
        this.workplaceUid = workplaceUid;
        this.approvedUid = userUid;
        this.createdId = request.getCreatedId();
    }
}
