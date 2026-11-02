package kware.apps.mobigen.mobigen.dto.request.metadata.create;


import lombok.Getter;
import lombok.Setter;


/**
* @fileName     CreateMetadata
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      [METADATA_03] 하나의 메타데이터 생성 요청 DTO
**/

@Getter @Setter
public class CreateMetadataRequest {

    private String action;                      // "create"
    private CreateMetadataFieldRequest field;

    public CreateMetadataRequest(CreateMetadataFieldRequest field) {
        this.action = "create";
        this.field = field;
    }
}
