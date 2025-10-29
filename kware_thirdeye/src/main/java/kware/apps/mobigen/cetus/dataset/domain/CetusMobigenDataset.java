package kware.apps.mobigen.cetus.dataset.domain;

import kware.apps.mobigen.integration.dto.request.metadata.ChangeMetadata;
import kware.apps.mobigen.integration.dto.request.metadata.SaveMetadata;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CetusMobigenDataset {

    private Long uid;
    private String title;
    private String extdata;

    public CetusMobigenDataset(String title, String extdata) {
        this.title = title;
        this.extdata = extdata;
    }

    public CetusMobigenDataset(SaveMetadata request) {
        this.title = request.getTitle();
        this.extdata = request.getExtdata();
    }

    public CetusMobigenDataset(Long uid, ChangeMetadata request) {
        this.uid = uid;
        this.title = request.getTitle();
        this.extdata = request.getExtdata();
    }

    public CetusMobigenDataset(Long uid) {
        this.uid = uid;
    }
}
