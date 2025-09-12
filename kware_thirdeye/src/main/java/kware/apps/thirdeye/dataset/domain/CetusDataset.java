package kware.apps.thirdeye.dataset.domain;


import cetus.bean.AuditBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusDataset extends AuditBean {

    private Long uid;
    private Long datasetId;         // 데이터셋 id
    private Long workplaceUid;      // 워크플레이스 uid
    private String approvedDt;      // 승인일시
    private Long approvedUid;       // 승인자 uid
    private String showAt;          // 공개 여부
    private String showUpdtAt;      // 공개 일시
    private Long showUpdtUid;       // 공개자 uid
    private Long createdUid;        // 데이터셋 등록자 uid
}
