package kware.apps.mobigen.cetus.dataset.domain;

import kware.apps.mobigen.cetus.dataset.dto.request.ChangeMobigenDataset;
import kware.apps.mobigen.cetus.dataset.dto.request.SaveMobigenDataset;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusMobigenDataset {

    private Long uid;
    private String title;
    private Long metadataFileUid;
    private Long realdataFileUid;
    private String registrantId;
    private String metadata;

    public CetusMobigenDataset(SaveMobigenDataset request, Long metadataFileUid, Long realdataFileUid, String registrantId) {
        this.title = request.getTitle();
        this.metadataFileUid = metadataFileUid;
        this.realdataFileUid = realdataFileUid;
        this.registrantId = registrantId;
        this.metadata = request.getMetadata();
    }

    public CetusMobigenDataset(Long uid, ChangeMobigenDataset request) {
        this.uid = uid;
        this.title = request.getTitle();
        this.metadata = request.getMetadata();
    }

    public CetusMobigenDataset(Long uid) {
        this.uid = uid;
    }
}
