package kware.apps.mobigen.cetus.tag.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusMobigenDatasetTag {

    private Long uid;
    private Long metadataId;
    private Long tagUid;

    public CetusMobigenDatasetTag(Long metadataId, Long tagUid) {
        this.metadataId = metadataId;
        this.tagUid = tagUid;
    }
}
