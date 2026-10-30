package kware.apps.system.group.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import cetus.annotation.DisplayName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupMerge {
    private Long uid;
    @NotBlank @Size(max = 64) @DisplayName("그룹 이름")
    private String groupNm;
    private Long sortNo;
}
