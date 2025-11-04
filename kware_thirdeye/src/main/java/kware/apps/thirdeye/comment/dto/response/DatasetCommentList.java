package kware.apps.thirdeye.comment.dto.response;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DatasetCommentList {
    private Long commentUid;
    private Long approvedUid;
    private String typeCd;
    private String typeStr;
    private Integer ratings;
    private String comment;
    private Long regUid;
    private String regNm;
    private String regDt;
    private String regProfileId;
}
