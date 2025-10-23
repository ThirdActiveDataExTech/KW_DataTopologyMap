package kware.apps.thirdeye.mobigen.datasetfile.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchDatasetFile {

    private String metadataId;
    private String rawdataId;
    private String dataTpCd;

    public SearchDatasetFile(String metadataId, String rawdataId, String dataTpCd) {
        this.metadataId = metadataId;
        this.rawdataId = rawdataId;
        this.dataTpCd = dataTpCd;
    }
}
