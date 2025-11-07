package kware.apps.mobigen.integration.dto.request.rawdata;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

/**
* @fileName     ChangeRawdata
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-23
* @summary      [RAWDATA_05] 원본데이터 단건 수정
**/

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeRawdata {
    private String rawdataId;
    private String metadataId;
    private String description;
    private List<String> tags;
}
