package kware.apps.mobigen.mobigen.dto.response.common;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* @fileName     RawdataResultResponse
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-16
* @summary      [COMMON] 실(원본)데이터에 대한 공통 응답 DTO
**/

@Getter @Setter @ToString
public class RawdataResultResponse {
    private String rawdata_id;
    private String filename;
    private String size;
    private String uploaded_at;
}
