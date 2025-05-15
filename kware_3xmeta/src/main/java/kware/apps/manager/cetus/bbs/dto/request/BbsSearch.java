package kware.apps.manager.cetus.bbs.dto.request;


import lombok.Getter;

@Getter
public class BbsSearch {

    private String useAt;
    private Long workplaceUid;

    public BbsSearch(String useAt, Long workplaceUid) {
        this.useAt = useAt;
        this.workplaceUid = workplaceUid;
    }

    public void setWorkplaceUid(Long workplaceUid) {
        this.workplaceUid = workplaceUid;
    }
}
