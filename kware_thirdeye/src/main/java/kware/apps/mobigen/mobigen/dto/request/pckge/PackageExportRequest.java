package kware.apps.mobigen.mobigen.dto.request.pckge;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PackageExportRequest {

    private String metadata_id;
    private List<String> rawdata_ids;
}
