package kware.apps.mobigen.mobigen.dto.request.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
* @fileName     SortRequest
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-16
* @summary      [COMMON] 조회를 위한 정렬 요청 DTO
**/

@Getter @Setter @NoArgsConstructor
public class SortFieldRequest {
    private String order;
    private String field;

    public SortFieldRequest(String order, String field) {
        this.order = order;
        this.field = field;
    }
}
