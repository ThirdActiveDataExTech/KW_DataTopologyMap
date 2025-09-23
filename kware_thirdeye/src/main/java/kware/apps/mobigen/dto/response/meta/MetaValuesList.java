package kware.apps.mobigen.dto.response.meta;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName     MetaValuesList
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      메타데이터로 사용되는 key의 value 정보, 메타데이터 검색시 사용
**/

@Getter @Setter
public class MetaValuesList {

    private String key;
    private List<String> values;
    private int total;

}
