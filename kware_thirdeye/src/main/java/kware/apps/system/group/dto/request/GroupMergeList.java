package kware.apps.system.group.dto.request;

import kware.apps.system.position.dto.request.PositionMerge;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupMergeList {
    private Long workplaceUid;
    private List<GroupMerge> groups;
    private Long[] uids;
}