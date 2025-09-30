package kware.apps.mobigen.cetus.category.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusMobigenDatasetCategory {

    private Long uid;
    private Long datasetUid;
    private Long categoryUid;

    public CetusMobigenDatasetCategory(Long datasetUid, Long categoryUid) {
        this.datasetUid = datasetUid;
        this.categoryUid = categoryUid;
    }

}
