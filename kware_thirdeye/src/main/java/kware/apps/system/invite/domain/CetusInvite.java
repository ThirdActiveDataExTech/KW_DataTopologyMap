package kware.apps.system.invite.domain;

import cetus.bean.AuditBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import cetus.annotation.DisplayName;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;

@Getter @NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CetusInvite extends AuditBean {

    private Long uid;
    @NotBlank @Email @Size(max = 255) @DisplayName("이메일")
    private String email;
    private String url;
    private LocalDateTime expirationDate;
    private String useAt;

    public void setInviteInfo(String url, LocalDateTime expirationDate) {
        this.url = url;
        this.expirationDate = expirationDate;
    }
}
