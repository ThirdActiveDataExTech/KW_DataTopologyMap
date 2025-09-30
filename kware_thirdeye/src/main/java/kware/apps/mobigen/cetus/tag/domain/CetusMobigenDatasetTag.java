package kware.apps.mobigen.cetus.tag.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusMobigenDatasetTag {

    private Long uid;
    private Long datasetUid;
    private Long tagUid;

    public CetusMobigenDatasetTag(Long datasetUid, Long tagUid) {
        this.datasetUid = datasetUid;
        this.tagUid = tagUid;
    }
}
