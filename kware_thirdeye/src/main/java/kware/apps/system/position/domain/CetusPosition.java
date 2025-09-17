package kware.apps.system.position.domain;

import cetus.bean.AuditBean;
import kware.apps.system.position.dto.request.PositionMerge;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusPosition extends AuditBean {

    private Long uid;
    private String name;
    private String description;
    private Long sortOrder;
    private String useAt;
    private Long workplaceUid;

    public CetusPosition(Long workplaceUid, PositionMerge request) {
        this.uid = request.getUid();
        this.name = request.getPositionNm();
        this.sortOrder = request.getSortNo();
        this.workplaceUid = workplaceUid;
    }

    public CetusPosition(Long uid) {
        this.uid = uid;
    }
}
