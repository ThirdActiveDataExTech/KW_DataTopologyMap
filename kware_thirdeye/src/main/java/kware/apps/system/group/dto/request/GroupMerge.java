package kware.apps.system.group.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupMerge {
    private Long uid;
    private String groupNm;
    private Long sortNo;
}
