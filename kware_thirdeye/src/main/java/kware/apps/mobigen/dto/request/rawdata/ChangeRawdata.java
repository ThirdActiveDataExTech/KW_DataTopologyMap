package kware.apps.mobigen.dto.request.rawdata;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName     ChangeRawdata
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      원본데이터의 상세 메타정보 수정
**/

@Getter @Setter
public class ChangeRawdata {

    private String description;
    private List<String> tags;

}
