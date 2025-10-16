package kware.apps.mobigen.mobigen.dto.response.metadata;


import lombok.Getter;
import lombok.Setter;

/**
* @fileName     ChangeMetadataResponse
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      [METADATA_05] 메타데이터의 업데이트 응답 DTO
**/

@Getter @Setter
public class ChangeMetadataResponse {
    private String metadata_id;
    private String updated_at;
}
