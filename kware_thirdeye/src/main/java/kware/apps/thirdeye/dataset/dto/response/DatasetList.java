package kware.apps.thirdeye.dataset.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DatasetList {

    private Long uid;               // cetus_approved_dataset.uid (pk)
    private Long datasetId;         // 데이터셋 ID
    private String approvedDt;      // 승인 일시
    private String createdNm;       // 데이터 등록자 이름
    private String approvedNm;      // 데이터 승인자 이름
    private Integer ratings;        // 데이터셋 의견/평점

    private String title;           // 데이터셋 제목
    private String description;     // 데이터셋 내용
    private String metadata;        // 데이터셋 메타데이터

    public void setDatasetInfo(String title, String description, String metadata) {
        this.title = title;
        this.description = description;
        this.metadata = metadata;
    }
}
