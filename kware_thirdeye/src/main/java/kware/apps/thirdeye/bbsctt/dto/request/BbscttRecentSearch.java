package kware.apps.thirdeye.bbsctt.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BbscttRecentSearch {

    private Long workplaceUid;
    private int count;
    private String bbsTpCd;

    public BbscttRecentSearch(Long workplaceUid, int count, String bbsTpCd) {
        this.workplaceUid = workplaceUid;
        this.count = count;
        this.bbsTpCd = bbsTpCd;
    }
}
