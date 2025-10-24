package kware.apps.mobigen.integration.dto.response.rawdata;


import kware.apps.mobigen.mobigen.dto.response.rawdata.RawdataListItemResponse;
import kware.apps.thirdeye.mobigen.datasetfile.dto.response.CetusDatasetFileView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class RawdataList {

    private String metadataId;
    private String rawdataId;

    private RawdataListItemResponse rawdataView;
    private CetusDatasetFileView rawdataFile;

    public RawdataList(String metadataId, String rawdataId) {
        this.metadataId = metadataId;
        this.rawdataId = rawdataId;
    }

}
