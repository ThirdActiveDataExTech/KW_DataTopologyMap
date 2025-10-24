package kware.apps.mobigen.integration.dto.request.rawdata;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DownloadRawdata {
    private String metadataId;
    private String rawdataId;
    private String rawdataFileId;
}
