package kware.apps.manager.cetus.form.domain;

import cetus.bean.AuditBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusColumnOptions extends AuditBean {
    private Long uid;
    private Long columnsUid;
    private String label;
    private String name;

    public void addUid(Long uid) {
        this.uid = uid;
    }

    public void addColumnsUid(Long columnsUid) {
        this.columnsUid = columnsUid;
    }

    public void addAuthor(Long regUid) {
        setRegUid(regUid);
        setUpdtUid(regUid);
    }
}
