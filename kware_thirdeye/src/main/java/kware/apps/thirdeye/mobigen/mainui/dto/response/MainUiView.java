package kware.apps.thirdeye.mobigen.mainui.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainUiView {

    private Long uid;
    private String code;
    private String name;
    private Integer maxCount;
    private Integer descLineCamp;
    private Integer titleLineCamp;
    private String thumbUseAt;
    private Long workplaceUid;
    private String useAt;
    private Long regUid;
    private String regNm;
    private String regDt;
}
