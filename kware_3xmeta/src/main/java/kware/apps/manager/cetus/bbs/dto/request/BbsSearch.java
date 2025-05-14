package kware.apps.manager.cetus.bbs.dto.request;


import lombok.Getter;

@Getter
public class BbsSearch {

    private String useAt;

    public BbsSearch(String useAt) {
        this.useAt = useAt;
    }
}
