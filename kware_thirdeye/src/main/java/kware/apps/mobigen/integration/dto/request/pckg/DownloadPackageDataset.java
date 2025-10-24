package kware.apps.mobigen.integration.dto.request.pckg;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class DownloadPackageDataset {

    private String metadataId;
    private List<String> rawdataIds;

}
