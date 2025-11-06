package kware.apps.thirdeye.mobigen.mainui.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainUiList {

    private Long uid;
    private String code;
    private String name;
    private Integer maxCount;
    private Integer categoryCnt;
    private Integer descLineClamp;
    private Integer titleLineClamp;
    private String thumbUseAt;
    private Long workplaceUid;
    private String useAt;
    private String typeCd;
    private String typeCdDescription;
    private Long sortNo;
    private Long regUid;
    private String regNm;
    private String regDt;

    public void setTypeCdDescription(String typeCdDescription) {
        this.typeCdDescription = typeCdDescription;
    }
}
