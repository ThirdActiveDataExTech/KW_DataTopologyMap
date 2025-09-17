package kware.apps.system.workplace.dto.response;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkplaceList {

    private Long uid;
    private String name;
    private Integer userCnt;
    private Long regUid;
    private String regDt;
    private String regNm;

}
