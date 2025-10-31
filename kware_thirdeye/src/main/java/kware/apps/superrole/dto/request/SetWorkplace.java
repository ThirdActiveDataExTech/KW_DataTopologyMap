package kware.apps.superrole.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetWorkplace {
    private Long workplaceUid;
    private String workplaceNm;
}
