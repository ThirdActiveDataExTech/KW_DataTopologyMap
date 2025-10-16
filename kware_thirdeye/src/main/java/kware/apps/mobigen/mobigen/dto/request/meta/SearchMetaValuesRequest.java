package kware.apps.mobigen.mobigen.dto.request.meta;

import lombok.Getter;
import lombok.Setter;

/**
* @fileName     SearchMetaValuesRequest
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      [META_02] 메타데이터로 사용되는 필터 key값의 value 정보 요청 DTO
**/

@Getter @Setter
public class SearchMetaValuesRequest {
    private String key;
}
