package kware.apps.mobigen.mobigen.dto.response.rawdata;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
* @fileName     ChangeRawdataResponse
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-16
* @summary      [RAWDATA_05] 원본데이터의 상세정보 수정 응답 DTO
**/

@Getter @Setter @ToString
public class ChangeRawdataResponse {
    private String description;
    private List<String> tags;
}
