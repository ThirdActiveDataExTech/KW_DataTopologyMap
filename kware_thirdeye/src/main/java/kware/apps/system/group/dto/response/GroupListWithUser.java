package kware.apps.system.group.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupListWithUser {

    private Long uid;
    private String name;
    private String hasUser;

}
