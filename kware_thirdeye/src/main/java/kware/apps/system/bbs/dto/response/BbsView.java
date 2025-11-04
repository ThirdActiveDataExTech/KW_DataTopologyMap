package kware.apps.system.bbs.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BbsView {
    private Long bbsUid;
    private String bbsNm;
    private String bbsTpCd;
    private String bbsTpSubCd;
    private String bbsTpCdNm;
    private String useAt;
    private String deleteAt;
    private String bbsClUseAt;
    private String atchAt;
    private Integer atchNum;
    private Integer uploadCpcty;
    private String answerUseAt;
}
