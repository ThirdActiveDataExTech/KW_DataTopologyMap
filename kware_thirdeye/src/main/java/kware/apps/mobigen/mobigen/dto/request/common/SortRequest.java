package kware.apps.mobigen.mobigen.dto.request.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
* @fileName     SortRequest
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-16
* @summary      [COMMON] 조회를 위한 정렬 요청 DTO
**/

@Getter @Setter @NoArgsConstructor @ToString
public class SortRequest {

    // 참고사항 sort field 옵션 : id, title, issued, modified, ingested_at, updated_at, publisher
    private String field;
    private String order;

    public SortRequest(String field, String order) {
        this.field = field;
        this.order = order;
    }
}
