package kware.apps.manager.cetus.program.dto.request;


import lombok.Getter;

@Getter
public class ProgrmInfoSearch {

    private Long workplaceUid;

    public ProgrmInfoSearch(Long workplaceUid) {
        this.workplaceUid = workplaceUid;
    }

    public void setWorkplaceUid(Long workplaceUid) {
        this.workplaceUid = workplaceUid;
    }
}
