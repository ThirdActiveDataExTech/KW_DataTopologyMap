package kware.apps.thirdeye.bbsctt.dto.response;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BbscttList {

    private Long bbscttUid;
    private Long bbsUid;
    private String bbsTpCd;
    private String bbsTpSubCd;
    private Long clUid;
    private String bbscttNm;
    private int rdCnt;
    private String bbscttCnt;
    private String noticeAt;
    private Long fileUid;
    private int fileCnt;
    private int answerCnt;
    private String useAt;
    private String openAt;
    private String deleteAt;
    private Long regUid;
    private String regDt;
    private String regNm;
    private String regEmail;
    private String regProfileId;
    private String thumbnailSrc;

    public void setBbs(String bbsTpSubCd) {
        this.bbsTpSubCd = bbsTpSubCd;
    }
}
