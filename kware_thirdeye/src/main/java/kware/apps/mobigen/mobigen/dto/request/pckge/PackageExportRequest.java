package kware.apps.mobigen.mobigen.dto.request.pckge;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
* @fileName     PackageExportRequest
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-16
* @summary      [PACKAGE_01] 데이터셋 다운로드 요청 DTO
**/

@Getter @Setter @NoArgsConstructor
public class PackageExportRequest {

    private String metadata_id;
    private List<String> rawdata_ids;

    public PackageExportRequest(String metadata_id, List<String> rawdata_ids) {
        this.metadata_id = metadata_id;
        this.rawdata_ids = rawdata_ids;
    }
}
