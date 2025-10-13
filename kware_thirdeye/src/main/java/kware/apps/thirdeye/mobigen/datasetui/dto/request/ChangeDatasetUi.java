package kware.apps.thirdeye.mobigen.datasetui.dto.request;

import cetus.bean.FileBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeDatasetUi extends FileBean {

    private Long mainUiUid;
    private String showAt;
    private Long sortNo;
    private Long thumbUid;
    private String extraJson;

}
