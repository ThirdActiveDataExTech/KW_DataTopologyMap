package kware.apps.portal.bbsctt.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BbscttTpSearch {

    private String bbsTpCd;
    private Long workplaceUid;

    public BbscttTpSearch(String bbsTpCd, Long workplaceUid) {
        this.bbsTpCd = bbsTpCd;
        this.workplaceUid = workplaceUid;
    }
}
