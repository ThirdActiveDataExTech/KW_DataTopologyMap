package kware.apps.system.position.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PositionMergeList {

    private Long workplaceUid;
    private List<PositionMerge> positions;
    private Long[] uids;
}
