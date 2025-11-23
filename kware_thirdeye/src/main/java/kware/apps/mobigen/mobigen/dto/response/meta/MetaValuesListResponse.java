package kware.apps.mobigen.mobigen.dto.response.meta;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
* @fileName     MetaValuesListResponse
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      [META_02] 메타데이터로 사용되는 필터 key값의 value 정보 응답 DTO
**/

@Getter @Setter @ToString
public class MetaValuesListResponse {
    private String filter;
    private List<String> values;
}
