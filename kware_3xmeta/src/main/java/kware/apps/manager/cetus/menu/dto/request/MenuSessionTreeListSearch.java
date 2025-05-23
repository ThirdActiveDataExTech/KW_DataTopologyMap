package kware.apps.manager.cetus.menu.dto.request;

import lombok.Getter;


@Getter
public class MenuSessionTreeListSearch {

    private String useAt;
    private String authorCd;
    private Long workplaceUid;
    private Long menuNo;

    public MenuSessionTreeListSearch(String useAt, String authorCd, Long workplaceUid) {
        this.useAt = useAt;
        this.authorCd = authorCd;
        this.workplaceUid = workplaceUid;
    }

    public MenuSessionTreeListSearch(String useAt, String authorCd, Long workplaceUid, Long menuNo) {
        this.useAt = useAt;
        this.authorCd = authorCd;
        this.workplaceUid = workplaceUid;
        this.menuNo = menuNo;
    }
}
