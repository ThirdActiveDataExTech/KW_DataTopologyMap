package kware.apps.manager.cetus.user.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserList {

    private Long uid;
    private String userId;
    private String userNm;
    private String userTel;
    private String userEmail;
    private String approveAt;
    private String useAt;
    private String regDt;
}
