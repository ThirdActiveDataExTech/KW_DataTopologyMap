package kware.apps.manager.cetus.user.domain;

import cetus.bean.AuditBean;
import kware.apps.manager.cetus.user.dto.request.UserChange;
import kware.apps.manager.cetus.user.dto.request.UserSave;
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

    /**
     * @method      CetusUser
     * @author      hdh
     * @date        2025-04-28
     * @deacription 유저 저장용 생성자
    **/
    public CetusUser(UserSave request, String encodePassword) {
        this.userId = request.getUserId();
        this.password = encodePassword;
        this.userNm = request.getUserNm();
    }

    /**
     * @method      CetusUser
     * @author      hdh
     * @date        2025-04-28
     * @deacription 유저 수정용 생성자 1
    **/
    public CetusUser(String userId) {
        this.userId = userId;
    }

    /**
     * @method      CetusUser
     * @author      hdh
     * @date        2025-04-28
     * @deacription 유저 수정용 생성자 2
    **/
    public CetusUser changeUser(Long uid, UserChange request, String encodePassword) {
        this.uid = uid;
        this.password = encodePassword;
        this.userNm = (request.getUserNm() != null) ? request.getUserNm() : this.userNm;
        return this;
    }
}
