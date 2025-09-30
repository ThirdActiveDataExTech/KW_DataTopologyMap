package kware.apps.mobigen.mobigen.dto.response.metadata;


import lombok.Getter;
import lombok.Setter;

/**
* @fileName     ChangeMetadataResponse
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      메타데이터의 업데이트 진행
**/

@Getter @Setter
public class ChangeMetadataResponse {

    private String metadata_id;
    private String updated_at;

}
