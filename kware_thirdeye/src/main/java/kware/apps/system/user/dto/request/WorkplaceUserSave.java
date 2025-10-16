package kware.apps.system.user.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkplaceUserSave {
    private String userId;
    private String password;
    private String userNm;
    private String userEmail;
    private String authorCd;
    private String status;

    @Builder
    public WorkplaceUserSave(String userId, String password, String userNm,
                             String userEmail, String authorCd, String status) {
        this.userId = userId;
        this.password = password;
        this.userNm = userNm;
        this.userEmail = userEmail;
        this.authorCd = authorCd;
        this.status = status;
    }
}
