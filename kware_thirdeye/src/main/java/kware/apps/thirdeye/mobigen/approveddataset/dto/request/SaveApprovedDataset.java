package kware.apps.thirdeye.mobigen.approveddataset.dto.request;


import cetus.bean.FileBean;
import kware.apps.thirdeye.mobigen.category.dto.request.SaveCategory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
* @fileName     SaveApprovedDataset
* @author       dahyeon
* @version      1.0.0
* @date         2025-10-29
* @summary      데이터셋 진열등록
**/

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveApprovedDataset extends FileBean {

    private Long metadataId;             // 진열관리,승인할 데이터셋 ID
    private Long mainUiUid;
    private String showAt;
    private Long sortNo;
    private String extraJson;
    private SaveCategory category;
    private String targetTpCd;      // 원본 데이터셋 저장 위치

}
