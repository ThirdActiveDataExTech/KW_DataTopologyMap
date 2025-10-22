package kware.apps.thirdeye.mobigen.datasetfile.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchDatasetFileView {

    private String metadataId;
    private String rawdataId;

    public SearchDatasetFileView(String metadataId, String rawdataId) {
        this.metadataId = metadataId;
        this.rawdataId = rawdataId;
    }
}
