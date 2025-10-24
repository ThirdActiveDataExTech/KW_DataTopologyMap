package kware.apps.mobigen.integration.dto.response.rawdata;

import kware.apps.mobigen.mobigen.dto.response.rawdata.ViewRawdataResponse;
import kware.apps.thirdeye.mobigen.datasetfile.dto.response.CetusDatasetFileView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class RawdataView {

    private String metadataId;
    private String rawdataId;

    // from. mobigen
    private ViewRawdataResponse rawdataResponse;

    // from. kware
    private CetusDatasetFileView rawdataFile;

    public RawdataView(String metadataId, String rawdataId) {
        this.metadataId = metadataId;
        this.rawdataId = rawdataId;
    }
}
