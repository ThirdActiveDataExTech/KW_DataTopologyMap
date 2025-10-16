package kware.apps.system.menu.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkplaceMenuSave {

    private Long programUid;
    private String menuNm;
    private String authorCd;
    private String rootMenuCd;
    private Long workplaceUid;
    private Long upperMenuNo;
    private int sortNo;

    @Builder
    public WorkplaceMenuSave(Long programUid, String menuNm, String authorCd, String rootMenuCd,
                             Long workplaceUid, Long upperMenuNo, int sortNo) {
        this.programUid = programUid;
        this.menuNm = menuNm;
        this.authorCd = authorCd;
        this.rootMenuCd = rootMenuCd;
        this.workplaceUid = workplaceUid;
        this.upperMenuNo = upperMenuNo;
        this.sortNo = sortNo;
    }
}
