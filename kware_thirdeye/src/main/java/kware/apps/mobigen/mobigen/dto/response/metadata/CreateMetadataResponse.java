package kware.apps.mobigen.mobigen.dto.response.metadata;


import kware.apps.mobigen.mobigen.dto.response.common.MetadataResultResponse;
import kware.apps.mobigen.mobigen.dto.response.common.RawdataResultResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* @fileName     CreateMetadataResponse
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      [METADATA_03] 하나의 메타데이터 생성 응답 DTO
**/

@Getter @Setter @ToString
public class CreateMetadataResponse {
    private MetadataResultResponse metadata;
    private RawdataResultResponse rawdata;
}
