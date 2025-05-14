package kware.apps.manager.cetus.bbsctt.domain;

import cetus.bean.AuditBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusBbsctt extends AuditBean {

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
    private Long thumbnailUid;

}
