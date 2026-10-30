package kware.apps.mobigen.integration.dto.request.rawdata;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import cetus.annotation.DisplayName;

import javax.validation.constraints.NotBlank;
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
    @NotBlank @DisplayName("원본데이터 ID")
    private String rawdataId;
    @NotBlank @DisplayName("메타데이터 ID")
    private String metadataId;
    private String extdata;
}
