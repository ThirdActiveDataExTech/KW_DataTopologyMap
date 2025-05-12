package kware.apps.manager.cetus.dept.deptuser.domain;

import cetus.bean.AuditBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusDeptUser extends AuditBean {

    private Long deptUid;
    private Long userUid;

    /**
     * @method      CetusDeptUser
     * @author      dahyeon
     * @date        2025-05-12
     * @deacription 그룹/부서 사용자 저장용 생성자
    **/
    public CetusDeptUser(Long deptUid, Long userUid) {
        this.deptUid = deptUid;
        this.userUid = userUid;
    }
}
