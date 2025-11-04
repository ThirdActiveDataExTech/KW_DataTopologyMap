package kware.apps.thirdeye.comment.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DatasetCommentCount {
    private String typeCd;
    private String typeCdNm;
    private Integer typeCnt;
}
