package kware.apps.thirdeye.bookmark.dto.response;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserBookMarkList {

    private Long userUid;                    // 유저 uid
    private Long approvedUid;                // cetus_dataset_bookmark.uid (pk)
    private Long datasetId;                  // 데이터셋 id
    private String title;                    // 데이터셋 제목
    private String description;              // 데이터셋 설명
    private String targetType;               // WISH_WORK, WISH_ARTIST

    public void setDatasetInfo(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
