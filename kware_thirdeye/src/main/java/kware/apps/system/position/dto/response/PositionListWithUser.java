package kware.apps.system.position.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PositionListWithUser {

    private Long uid;
    private String name;
    private String hasUser;
}
