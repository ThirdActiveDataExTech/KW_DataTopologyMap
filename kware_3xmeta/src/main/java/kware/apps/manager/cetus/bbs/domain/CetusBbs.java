package kware.apps.manager.cetus.bbs.domain;

import cetus.bean.AuditBean;
import kware.apps.manager.cetus.bbs.dto.request.BbsChange;
import kware.apps.manager.cetus.bbs.dto.request.BbsSave;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusBbs extends AuditBean {

    private Long bbsUid;
    private String bbsNm;
    private String bbsTpCd;
    private String useAt;
    private String deleteAt;

    public CetusBbs(BbsSave request) {
        this.bbsNm = request.getBbsNm();
        this.bbsTpCd = request.getBbsTpCd();
        this.useAt = request.getUseAt();
    }

    public CetusBbs changeBbs(Long uid, BbsChange request) {
        this.bbsUid = uid;
        this.bbsNm = (request.getBbsNm() != null) ? request.getBbsNm() : this.bbsNm;
        this.bbsTpCd = (request.getBbsTpCd() != null) ? request.getBbsTpCd() : this.bbsTpCd;
        this.useAt = (request.getUseAt() != null) ? request.getUseAt() : this.useAt;
        return this;
    }
}
