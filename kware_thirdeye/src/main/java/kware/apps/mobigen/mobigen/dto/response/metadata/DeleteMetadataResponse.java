package kware.apps.mobigen.mobigen.dto.response.metadata;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
* @fileName     DeleteMetadataResponse
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      [METADATA_02] 한개 이상의 메타데이터 삭제 응답 DTO
**/

@Getter @Setter @ToString
public class DeleteMetadataResponse {
    private List<String> deleted_ids;
}
