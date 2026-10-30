package kware.apps.system.position.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import cetus.annotation.DisplayName;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PositionMergeList {

    @NotNull @DisplayName("워크플레이스")
    private Long workplaceUid;
    @NotNull @Valid @DisplayName("직위 목록")
    private List<PositionMerge> positions;
    private Long[] uids;
}
