package kware.apps.mobigen.cetus.dataset.domain;

import kware.apps.mobigen.cetus.dataset.dto.request.ChangeMobigenDataset;
import kware.apps.mobigen.integration.dto.request.pckg.SaveMetadata;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusMobigenDataset {

    private Long uid;
    private String title;
    private String metadata;

    public CetusMobigenDataset(String title, String metadata) {
        this.title = title;
        this.metadata = metadata;
    }

    public CetusMobigenDataset(SaveMetadata request) {
        this.title = request.getTitle();
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
