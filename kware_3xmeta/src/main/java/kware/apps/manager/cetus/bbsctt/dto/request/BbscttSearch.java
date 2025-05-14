package kware.apps.manager.cetus.bbsctt.dto.request;


import lombok.Getter;

@Getter
public class BbscttSearch {

    private Long bbsUid;

    public BbscttSearch(Long bbsUid) {
        this.bbsUid = bbsUid;
    }
}
