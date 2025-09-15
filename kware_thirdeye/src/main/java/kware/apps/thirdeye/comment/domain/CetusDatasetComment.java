package kware.apps.thirdeye.comment.domain;

import cetus.bean.AuditBean;
import kware.apps.thirdeye.comment.dto.request.DatasetCommentSave;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusDatasetComment extends AuditBean {

    private Long uid;
    private Long approvedDatasetUid;
    private String typeCd;
    private Integer ratings;
    private String comment;

    public CetusDatasetComment(DatasetCommentSave request) {
        this.approvedDatasetUid = request.getApprovedUid();
        this.typeCd = request.getTypeCd();
        this.ratings = request.getRatings();
        this.comment = request.getComment();
    }

}
