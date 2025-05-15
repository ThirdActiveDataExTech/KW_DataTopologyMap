package kware.apps.manager.cetus.user.domain;

import cetus.bean.AuditBean;
import kware.apps.manager.cetus.user.dto.request.UserChange;
import kware.apps.manager.cetus.user.dto.request.UserChangeInfo;
import kware.apps.manager.cetus.user.dto.request.UserSave;
import kware.apps.manager.cetus.user.dto.request.UserSaveAdmin;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CetusUser extends AuditBean {

    private Long uid;
    private String userId;
    private String password;
    private String userNm;
    private String authorCd;
    private String status;
    private int failCnt;
    private Long profileUid;
    private String metaData;
    private String useAt;
    private String approveAt;
    private String userEmail;

    public CetusUser(UserSave request, String encodePassword) {
        this.userId = request.getUserId();
        this.password = encodePassword;
        this.userNm = request.getUserNm();
        this.userEmail = request.getUserEmail();
        this.authorCd = request.getAuthorCd();
        this.status = request.getStatus();
    }

    public CetusUser(UserSaveAdmin request, String encodePassword) {
        this.userId = request.getUserId();
        this.password = encodePassword;
        this.userNm = request.getUserNm();
        this.userEmail = request.getUserEmail();
        this.authorCd = request.getAuthorCd();
        this.status = request.getStatus();
    }

    public CetusUser(String userId) {
        this.userId = userId;
    }

    public CetusUser changeUser(Long uid, UserChange request) {
        this.uid = uid;
        this.userNm = (request.getUserNm() != null) ? request.getUserNm() : this.userNm;
        this.userEmail = (request.getUserEmail() != null) ? request.getUserEmail() : this.userEmail;
        this.authorCd =  (request.getUserAuthor() != null) ? request.getUserAuthor() : this.authorCd;
        return this;
    }

    public CetusUser changeUserPassword(Long uid, String encodePassword) {
        this.uid = uid;
        this.password = (encodePassword != null) ? encodePassword : this.password;
        return this;
    }

    public CetusUser(String code, String value, Long uid) {
        if("STATUS".equals(code)) {

            this.status = value;
            this.uid = uid;

        } else if("AUTHOR".equals(code)) {

            this.authorCd = value;
            this.uid = uid;

        }
    }

    public CetusUser(Long uid, Long profileUid) {
        this.uid = uid;
        this.profileUid = profileUid;
    }
}
