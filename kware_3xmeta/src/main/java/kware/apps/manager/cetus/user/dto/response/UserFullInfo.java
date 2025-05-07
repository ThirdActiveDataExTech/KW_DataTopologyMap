package kware.apps.manager.cetus.user.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFullInfo {

    private Long uid;
    private String userId;
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

}
