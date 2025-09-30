package kware.apps.mobigen.cetus.dataset.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusMobigenDataset {

    private Long uid;
    private String title;
    private String description;
    private String category;
    private Long metadataFileUid;
    private Long realdataFileUid;
    private String registrantId;
    private String metadata;

}
