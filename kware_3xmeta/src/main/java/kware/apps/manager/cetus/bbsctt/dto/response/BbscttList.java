package kware.apps.manager.cetus.bbsctt.dto.response;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BbscttList {

    private Long bbscttUid;
    private Long bbsUid;
    private Long clUid;
    private String bbscttNm;
    private int rdCnt;
    private String bbscttCnt;
    private String noticeAt;
    private Long fileUid;
    private String useAt;
    private String openAt;
    private String deleteAt;
    private Long regUid;
    private String regDt;
    private String regNm;
    private Long thumbnailUid;
    private String thumbnailId;
}
