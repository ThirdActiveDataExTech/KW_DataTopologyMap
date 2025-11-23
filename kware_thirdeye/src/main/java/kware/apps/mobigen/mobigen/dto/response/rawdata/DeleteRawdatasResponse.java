package kware.apps.mobigen.mobigen.dto.response.rawdata;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
* @fileName     DeleteRawdatasResponse
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      [RAWDATA_03] 원본데이터 여러건 삭제 응답 DTO
**/

@Getter @Setter @ToString
public class DeleteRawdatasResponse {
    private List<String> deleted_ids;
}
