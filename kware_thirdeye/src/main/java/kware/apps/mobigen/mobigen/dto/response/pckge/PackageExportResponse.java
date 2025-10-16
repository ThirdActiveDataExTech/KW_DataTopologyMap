package kware.apps.mobigen.mobigen.dto.response.pckge;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName     PackageExportResponse
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      [PACKAGE_01] 데이터셋 다운로드 응답 DTO
**/

@Getter @Setter
public class PackageExportResponse {

    private String metadata_id;
    private List<String> rawdata_ids;

}
