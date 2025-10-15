package kware.apps.thirdeye.mobigen.approveddataset.dto.request;


import cetus.bean.FileBean;
import kware.apps.thirdeye.mobigen.category.dto.request.SaveCategory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveApprovedDataset extends FileBean {

    private Long datasetId;             // 진열관리,승인할 데이터셋 ID
    private String createdId;           //
    private Long mainUiUid;
    private String showAt;
    private Long sortNo;
    private String extraJson;
    private SaveCategory category;

}
