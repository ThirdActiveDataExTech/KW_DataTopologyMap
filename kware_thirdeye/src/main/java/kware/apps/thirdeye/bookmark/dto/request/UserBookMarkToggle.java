package kware.apps.thirdeye.bookmark.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserBookMarkToggle {

    private Long datasetId;

    public UserBookMarkToggle(Long datasetId) {
        this.datasetId = datasetId;
    }
}
