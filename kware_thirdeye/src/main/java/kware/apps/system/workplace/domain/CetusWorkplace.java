package kware.apps.system.workplace.domain;

import cetus.bean.AuditBean;
import kware.apps.system.workplace.dto.request.WorkplaceSave;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusWorkplace extends AuditBean {

    private Long uid;
    private String name;

    public CetusWorkplace(WorkplaceSave request) {
        this.name = request.getWorkplaceNm();
    }

    public CetusWorkplace(Long uid, WorkplaceSave request) {
        this.uid = uid;
        this.name = request.getWorkplaceNm();
    }
}
