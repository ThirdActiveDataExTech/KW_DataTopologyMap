package kware.apps.thirdeye.comment.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DatasetCommentSave {
    private Long approvedUid;
    private String typeCd;
    private Integer ratings;
    private String comment;
}
