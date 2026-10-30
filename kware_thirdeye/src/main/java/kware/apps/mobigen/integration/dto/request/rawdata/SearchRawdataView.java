package kware.apps.mobigen.integration.dto.request.rawdata;

import lombok.Getter;
import lombok.Setter;

import cetus.annotation.DisplayName;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class SearchRawdataView {
    @NotBlank @DisplayName("메타데이터 ID")
    private String metadataId;
    @NotBlank @DisplayName("원본데이터 ID")
    private String rawdataId;

}
