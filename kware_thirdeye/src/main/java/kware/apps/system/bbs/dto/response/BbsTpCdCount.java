package kware.apps.system.bbs.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BbsTpCdCount {
    private String bbsTpCd;
    private String bbsTpCdNm;
    private Integer bbsCnt;
}
