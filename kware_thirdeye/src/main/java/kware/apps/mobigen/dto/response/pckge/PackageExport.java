package kware.apps.mobigen.dto.response.pckge;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName     PackageExport
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      데이터셋을 다운로드
**/

@Getter @Setter
public class PackageExport {

    private String metadata_id;
    private List<String> rawdata_ids;

}
