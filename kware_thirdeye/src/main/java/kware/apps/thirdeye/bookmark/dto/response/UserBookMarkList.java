package kware.apps.thirdeye.bookmark.dto.response;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserBookMarkList {

    private Long uid;                        // cetus_dataset_bookmark.uid (pk)
    private Long userUid;                    // 유저 uid
    private Long datasetId;                  // 데이터셋 id
    private Long approvedDatasetUid;         // cetus_approved_dataset.uid
    private String title;                    // 데이터셋 제목
    private String description;              // 데이터셋 설명

    public void setDatasetInfo(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
