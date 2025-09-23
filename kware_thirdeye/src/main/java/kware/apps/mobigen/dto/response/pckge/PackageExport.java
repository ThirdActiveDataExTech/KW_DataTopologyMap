package kware.apps.mobigen.dto.response.pckge;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PackageExport {

    private String metadata_id;
    private List<String> rawdata_ids;

}
