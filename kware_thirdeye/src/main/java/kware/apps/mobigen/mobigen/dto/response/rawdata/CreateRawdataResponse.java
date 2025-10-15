package kware.apps.mobigen.mobigen.dto.response.rawdata;


import lombok.Getter;
import lombok.Setter;

/**
* @fileName     CreateRawdataResponse
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      원본데이터 추가 작업
 *              => 메타데이터 하위 원본데이터 추가에 대한 응답
**/

@Getter @Setter
public class CreateRawdataResponse {

    private String rawdata_id;
    private String filename;
    private int file_size;
    private String updated_at;

}
