package kware.apps.thirdeye.mobigen.approveddataset.dto.request;


import cetus.bean.FileBean;
import kware.apps.thirdeye.mobigen.category.dto.request.SaveCategory;
import lombok.AccessLevel;
import cetus.annotation.DisplayName;
import cetus.annotation.YOrN;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
* @fileName     SaveApprovedDataset
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-29
* @summary      데이터셋 진열등록
**/

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveApprovedDataset extends FileBean {

    @NotBlank @Size(max = 100) @DisplayName("데이터셋 ID")
    private String metadataId;      // 진열관리,승인할 데이터셋 ID
    @NotNull @DisplayName("메인 UI")
    private Long mainUiUid;
    @YOrN(allowNull = false) @DisplayName("공개 여부")
    private String showAt;
    @NotNull @DisplayName("정렬 순서")
    private Long sortNo;
    private String extraJson;
    @NotNull @Valid @DisplayName("카테고리")
    private SaveCategory category;
    @NotBlank @Size(max = 32) @DisplayName("원본 저장 위치")
    private String targetTpCd;      // 원본 데이터셋 저장 위치

}
