package kware.apps.system.invite.domain;

import cetus.bean.AuditBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter @NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CetusInvite extends AuditBean {

    private Long uid;
    private String email;
    private String url;
    private LocalDateTime expirationDate;
    private String useAt;

    public void setInviteInfo(String url, LocalDateTime expirationDate) {
        this.url = url;
        this.expirationDate = expirationDate;
    }
}
