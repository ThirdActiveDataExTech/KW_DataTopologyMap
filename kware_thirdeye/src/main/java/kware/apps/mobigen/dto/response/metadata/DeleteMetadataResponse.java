package kware.apps.mobigen.dto.response.metadata;


import kware.apps.mobigen.dto.response.ApiResponse;
import lombok.Getter;
import lombok.Setter;

/**
* @fileName     DeleteMetadataResponse
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      메타데이터의 삭제 진행
**/

@Getter @Setter
public class DeleteMetadataResponse extends ApiResponse<DeleteMetadataResponse> {

    private String metadata_id;
    private String deleted_at;

}
