package kware.apps.mobigen.mobigen.dto.request.rawdata;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName     DeleteRawdatasRequest
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      [RAWDATA_03] 원본데이터 여러건 삭제 요청 DTO
**/

@Getter @Setter
public class DeleteRawdatasRequest {
    private String action;              // "delete"
    private String metadata_id;
    private List<String> rawdata_ids;
}
