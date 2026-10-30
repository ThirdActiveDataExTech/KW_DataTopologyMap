package kware.apps.system.group.dto.request;

import kware.apps.system.position.dto.request.PositionMerge;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import cetus.annotation.DisplayName;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupMergeList {
    @NotNull @DisplayName("워크플레이스")
    private Long workplaceUid;
    @NotNull @Valid @DisplayName("그룹 목록")
    private List<GroupMerge> groups;
    private Long[] uids;
}