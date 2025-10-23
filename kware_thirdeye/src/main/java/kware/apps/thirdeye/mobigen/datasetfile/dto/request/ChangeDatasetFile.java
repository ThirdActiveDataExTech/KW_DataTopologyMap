package kware.apps.thirdeye.mobigen.datasetfile.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeDatasetFile {

    private String metadataId;
    private String rawdataId;
    private String fileId;

    public ChangeDatasetFile(String metadataId, String rawdataId, String fileId) {
        this.metadataId = metadataId;
        this.rawdataId = rawdataId;
        this.fileId = fileId;
    }
}
