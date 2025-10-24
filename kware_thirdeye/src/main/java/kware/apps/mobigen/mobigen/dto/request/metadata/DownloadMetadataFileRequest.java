package kware.apps.mobigen.mobigen.dto.request.metadata;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
* @fileName     DownloadMetadataFileRequest
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-15
* @summary      [METADATA_08] 특정 메타데이터 다운로드 요청 DTO
**/

@Getter @Setter @NoArgsConstructor
public class DownloadMetadataFileRequest {
    private String metadata_id;

    public DownloadMetadataFileRequest(String metadata_id) {
        this.metadata_id = metadata_id;
    }
}
