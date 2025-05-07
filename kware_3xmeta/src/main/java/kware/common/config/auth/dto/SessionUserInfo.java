package kware.common.config.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kware.apps.manager.cetus.menu.domain.CetusMenu;
import kware.apps.manager.cetus.user.dto.response.UserFullInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
@EqualsAndHashCode(of = "userId", callSuper = false)
public class SessionUserInfo implements Serializable {
    private Long uid;
    private String userId;
    @JsonIgnore
    private String password;
    private String userNm;
    private String userTel;
    private String userEmail;
    private String approveAt;
    private String useAt;
    private String lastConnDt;
    private String lastPasswordChangeDe;
    private String userSttusCd;
    private Integer failCnt;
    private String deleteAt;
    private String role;
    private String roleNm;
    private List<String> authorizedMenuUrls;
    private List<CetusMenu> menus;

    public SessionUserInfo(UserFullInfo user) {
        this.uid = user.getUid();
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.userNm = user.getUserNm();
        this.userTel = user.getUserTel();
        this.userEmail = user.getUserEmail();
        this.approveAt = user.getApproveAt();
        this.useAt = user.getUseAt();
        this.lastConnDt = user.getLastConnDt();
        this.lastPasswordChangeDe = user.getLastPasswordChangeDe();
        this.userSttusCd = user.getUserSttusCd();
        this.failCnt = user.getFailCnt();
        this.deleteAt = user.getDeleteAt();
        this.role = user.getRole();
        this.roleNm = user.getRoleNm();
    }

    public void addMenuAndAuthorizedMenuUrls(List<CetusMenu> menus, List<String> authorizedMenuUrls) {
        this.menus = menus;
        this.authorizedMenuUrls = authorizedMenuUrls;
    }

}
