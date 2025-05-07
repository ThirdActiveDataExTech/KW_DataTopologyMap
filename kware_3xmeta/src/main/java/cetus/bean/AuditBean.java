package cetus.bean;

import lombok.Getter;

@Getter
public class AuditBean {

    private Long regUid;
    private Long updtUid;

    public void setRegUid(Long regUid) {
        this.regUid = regUid;
    }
    public void setUpdtUid(Long updtUid) {
        this.updtUid = updtUid;
    }
}
