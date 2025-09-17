package kware.apps.system.dept.domain;


import cetus.bean.AuditBean;
import kware.apps.system.dept.dto.request.DeptTreeChange;
import kware.apps.system.dept.dto.request.DeptTreeSave;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusDept extends AuditBean {

    private Long uid;
    private String name;
    private String description;
    private Long sortOrder;
    private String useAt;
    private Long workplaceUid;
    private Long upperUid;

    public CetusDept(DeptTreeSave request) {
        this.name = request.getDeptNm();
        this.sortOrder = request.getSortNo();
        this.description = request.getDeptNm();
        this.workplaceUid = request.getWorkplaceUid();
        this.upperUid = request.getUpperUid();
    }

    public CetusDept(Long uid, DeptTreeChange request) {
        this.uid = uid;
        this.name = request.getDeptNm();
        this.sortOrder = request.getSortNo();
    }

    public CetusDept(Long uid) {
        this.uid = uid;
    }
}
