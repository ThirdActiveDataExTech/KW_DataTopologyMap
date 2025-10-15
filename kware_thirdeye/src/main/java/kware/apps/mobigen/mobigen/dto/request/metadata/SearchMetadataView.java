package kware.apps.mobigen.mobigen.dto.request.metadata;

import lombok.Getter;
import lombok.Setter;

/**
* @fileName     SearchMetadataView
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-15
* @summary      특정 메타데이터 상세 정보 조회
**/

@Getter @Setter
public class SearchMetadataView {

    private String action;          // "detail"
    private String metadata_id;
}
