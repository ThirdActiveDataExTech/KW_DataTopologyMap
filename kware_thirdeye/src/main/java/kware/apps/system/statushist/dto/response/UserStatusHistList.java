package kware.apps.system.statushist.dto.response;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserStatusHistList {

    private Long histUid;
    private Long userUid;
    private String reason;
    private String status;
    private String statusNm;
    private Long regUid;
    private String regDt;
    private String regId;
    private String regNm;
}
