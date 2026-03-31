package kware.apps.mobigen.mobigen.dto.request.common;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* @fileName     PaginationRequest
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-16
* @summary      [COMMON] 조회를 위한 페이징 요청 DTO
**/

@Getter @Setter @ToString
public class PaginationRequest {

    private int page;
    private int limit;

    public PaginationRequest(int page, int limit, boolean startZero) {
        this.page = startZero ? Math.max(page - 1, 0) : page;
        this.limit = limit;
    }
}
