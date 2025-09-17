package kware.apps.system.group.domain;


import cetus.bean.AuditBean;
import kware.apps.system.group.dto.request.GroupMerge;
import kware.apps.system.position.dto.request.PositionMerge;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusGroup extends AuditBean {

    private Long uid;
    private String name;
    private String description;
    private Long sortOrder;
    private String useAt;
    private Long workplaceUid;

    public CetusGroup(Long workplaceUid, GroupMerge request) {
        this.uid = request.getUid();
        this.name = request.getGroupNm();
        this.sortOrder = request.getSortNo();
        this.workplaceUid = workplaceUid;
    }

    public CetusGroup(Long uid) {
        this.uid = uid;
    }

}
