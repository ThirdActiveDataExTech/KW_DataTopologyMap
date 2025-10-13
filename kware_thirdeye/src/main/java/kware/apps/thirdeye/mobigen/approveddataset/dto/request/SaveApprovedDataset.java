package kware.apps.thirdeye.mobigen.approveddataset.dto.request;


import cetus.bean.FileBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveApprovedDataset extends FileBean {

    private Long datasetId;
    private String createdId;
    private Long mainUiUid;
    private String showAt;
    private Long sortNo;
    private String extraJson;

}
