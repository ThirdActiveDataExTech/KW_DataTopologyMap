package kware.apps.mobigen.mobigen.dto.response.rawdata;


import lombok.Getter;
import lombok.Setter;

/**
* @fileName     DeleteRawdatasResponse
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      [RAWDATA_03] 원본데이터 여러건 삭제 응답 DTO
**/

@Getter @Setter
public class DeleteRawdatasResponse {
    private int deleted_count;
}
