package kware.apps.mobigen.mobigen.dto.response.meta;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName     MetaKeysList
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary     [META_01] 메타데이터로 사용되는 필터 key 값의 목록 정보 응답 DTO
**/

@Getter @Setter
public class MetaKeysListResponse {
    public List<String> filters;
}
