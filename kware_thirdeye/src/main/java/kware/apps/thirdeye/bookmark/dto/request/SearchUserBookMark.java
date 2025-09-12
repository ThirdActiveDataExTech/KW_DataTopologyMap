package kware.apps.thirdeye.bookmark.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchUserBookMark {

    private Long userUid;

    public SearchUserBookMark(Long userUid) {
        this.userUid = userUid;
    }
}
