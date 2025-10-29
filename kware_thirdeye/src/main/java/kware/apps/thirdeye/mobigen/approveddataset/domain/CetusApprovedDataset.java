package kware.apps.thirdeye.mobigen.approveddataset.domain;


import kware.apps.thirdeye.mobigen.approveddataset.dto.request.SaveApprovedDataset;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusApprovedDataset {

    private Long uid;
    private Long metadataId;        // 데이터셋 id
    private Long workplaceUid;      // 워크플레이스 uid
    private String approvedDt;      // 승인일시
    private Long approverUid;       // 승인자 uid
    private String deleteAt;        // 삭제 여부
    private String targetTpCd;      // 원본 데이터셋 저장 위치
    private String searchData;      // 필터링을 위한 기본 데이터 정보

    public CetusApprovedDataset(SaveApprovedDataset request, Long workplaceUid, Long userUid) {
        this.metadataId = request.getMetadataId();
        this.workplaceUid = workplaceUid;
        this.approverUid = userUid;
        this.targetTpCd = request.getTargetTpCd();
    }

    public CetusApprovedDataset(Long metadataId, String searchData) {
        this.metadataId = metadataId;
        this.searchData = searchData;
    }

    public CetusApprovedDataset(Long uid) {
        this.uid = uid;
    }
}
