package kware.apps.mobigen.mobigen.dto.response.pckge;

import kware.apps.mobigen.mobigen.dto.response.common.MetadataResultResponse;
import kware.apps.mobigen.mobigen.dto.response.common.RawdataResultResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName     PackageImportResponse
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      [PACKAGE_02] 패키지 파일 업로드 응답 DTO
**/

@Getter @Setter
public class PackageImportResponse {
    private MetadataResultResponse metadata;
    private RawdataResultResponse rawdata;
}
