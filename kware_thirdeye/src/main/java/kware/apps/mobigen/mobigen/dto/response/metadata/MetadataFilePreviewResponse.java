package kware.apps.mobigen.mobigen.dto.response.metadata;

import kware.apps.mobigen.mobigen.dto.response.common.MetadataResultResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
* @fileName     MetadataFilePreviewResponse
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-15
* @summary      [METADATA_07] 메타데이터 파일 정보 미리보기 응답 DTO
**/

@Getter @Setter @ToString
public class MetadataFilePreviewResponse {
    private MetadataResultResponse metadata;
}
