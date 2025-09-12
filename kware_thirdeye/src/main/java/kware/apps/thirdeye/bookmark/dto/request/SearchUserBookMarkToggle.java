package kware.apps.thirdeye.bookmark.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchUserBookMarkToggle {

    private Long userUid;
    private Long datasetId;

    public SearchUserBookMarkToggle( Long userUid, Long datasetId ) {
        this.userUid = userUid;
        this.datasetId = datasetId;
    }
}
