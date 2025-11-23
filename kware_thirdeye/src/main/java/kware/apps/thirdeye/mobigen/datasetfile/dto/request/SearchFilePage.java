package kware.apps.thirdeye.mobigen.datasetfile.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchFilePage {

    private String metadataId;
    private String dataTpCd;

    public SearchFilePage(String metadataId, String dataTpCd) {
        this.metadataId = metadataId;
        this.dataTpCd = dataTpCd;
    }
}
