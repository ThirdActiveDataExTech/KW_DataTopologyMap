package kware.apps.thirdeye.bookmark.dto.response;


import kware.apps.thirdeye.mobigen.approveddataset.dto.response.ApprovedDatasetView;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserBookMarkList {

    private Long userUid;                    // 유저 uid
    private Long approvedUid;                // cetus_dataset_bookmark.uid (pk)
    private Long metadataId;                 // 데이터셋 id

    private ApprovedDatasetView datasetView;      // {metadataId} 값을 통해 모비젠에서 가져온 정보들

    public void setDatasetView(ApprovedDatasetView datasetView) {
        this.datasetView = datasetView;
    }
}
