package kware.apps.thirdeye.mobigen.datasetui.dto.response;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DatasetUiView {

    private Long approvedDatasetUid;
    private Long sortNo;
    private Long mainUiUid;
    private String showAt;
    private Long thumbUid;
    private String thumbId;
    private String extraJson;

}
