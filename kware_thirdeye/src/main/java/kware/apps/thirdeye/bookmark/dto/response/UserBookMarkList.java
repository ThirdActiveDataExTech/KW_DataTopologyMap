package kware.apps.thirdeye.bookmark.dto.response;


import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetView;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserBookMarkList {

    private Long userUid;                    // 유저 uid
    private Long approvedUid;                // cetus_dataset_bookmark.uid (pk)
    private Long datasetId;                  // 데이터셋 id
    private String targetType;               // WISH_WORK, WISH_ARTIST

    private MobigenDatasetView mobigenDatasetView;      // {datasetId} 값을 통해 모비젠에서 가져온 정보들

    public void setMobigenDatasetView(MobigenDatasetView mobigenDatasetView) {
        this.mobigenDatasetView = mobigenDatasetView;
    }
}
