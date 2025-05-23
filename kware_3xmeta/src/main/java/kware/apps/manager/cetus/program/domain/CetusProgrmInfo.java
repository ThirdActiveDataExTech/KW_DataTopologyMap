package kware.apps.manager.cetus.program.domain;


import cetus.bean.AuditBean;
import kware.apps.manager.cetus.program.dto.request.ProgramChange;
import kware.apps.manager.cetus.program.dto.request.ProgramSave;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusProgrmInfo extends AuditBean {

    private Long uid;
    private String progrmNm;
    private String url;
    private String progrmDc;
    private String useAt;
    private Long workplaceUid;

    public CetusProgrmInfo(ProgramSave request, Long workplaceUid) {
        this.progrmNm = request.getProgrmNm();
        this.url = request.getUrl();
        this.progrmDc = request.getProgrmDc();
        this.useAt = request.getUseAt();
        this.workplaceUid = workplaceUid;
    }

    public CetusProgrmInfo changeProgram(Long uid, ProgramChange request) {
        this.uid = uid;
        this.progrmNm = (request.getProgrmNm() != null) ? request.getProgrmNm() : this.progrmNm;
        this.url = (request.getUrl() != null) ? request.getUrl() : this.url;
        this.progrmDc = (request.getProgrmDc() != null) ? request.getProgrmDc() : this.progrmDc;
        this.useAt = (request.getUseAt() != null) ? request.getUseAt() : this.useAt;
        return this;
    }
}
