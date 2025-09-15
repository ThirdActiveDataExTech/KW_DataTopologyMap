package kware.apps.thirdeye.bookmark.domain;

import cetus.bean.AuditBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusBookMark extends AuditBean {

    private Long uid;
    private Long userUid;
    private Long approvedUid;

    public CetusBookMark(Long userUid, Long approvedUid) {
        this.userUid = userUid;
        this.approvedUid = approvedUid;
    }

}
