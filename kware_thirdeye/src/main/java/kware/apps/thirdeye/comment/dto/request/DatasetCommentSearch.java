package kware.apps.thirdeye.comment.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DatasetCommentSearch {

    private Long approvedUid;
    private String typeCd;

    public DatasetCommentSearch(Long approvedUid, String typeCd) {
        this.approvedUid = approvedUid;
        this.typeCd = typeCd;
    }

    public void setTypeCd(String typeCd) {
        this.typeCd = typeCd;
    }
}
