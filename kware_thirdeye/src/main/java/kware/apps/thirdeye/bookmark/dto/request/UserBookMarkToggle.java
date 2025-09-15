package kware.apps.thirdeye.bookmark.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserBookMarkToggle {

    private Long approvedUid;

    public UserBookMarkToggle(Long approvedUid) {
        this.approvedUid = approvedUid;
    }
}
