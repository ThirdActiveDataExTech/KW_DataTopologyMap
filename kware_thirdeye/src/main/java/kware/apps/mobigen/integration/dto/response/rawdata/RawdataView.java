package kware.apps.mobigen.integration.dto.response.rawdata;

import kware.apps.mobigen.mobigen.dto.response.rawdata.ViewRawdataResponse;
import kware.apps.thirdeye.mobigen.datasetfile.dto.response.CetusDatasetFileView;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RawdataView {

    // from. mobigen
    private ViewRawdataResponse rawdataResponse;

    // from. kware
    private CetusDatasetFileView rawdataFile;
}
