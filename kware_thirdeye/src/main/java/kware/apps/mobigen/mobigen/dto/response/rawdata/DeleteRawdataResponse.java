package kware.apps.mobigen.mobigen.dto.response.rawdata;


import lombok.Getter;
import lombok.Setter;

/**
* @fileName     DeleteRawdataResponse
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      (다수) 원본데이터 삭제 작업
**/

@Getter @Setter
public class DeleteRawdataResponse {

    private int deleted_count;
}
