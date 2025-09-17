package kware.apps.system.position.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PositionMerge {

    private Long uid;
    private String positionNm;
    private Long sortNo;
}
