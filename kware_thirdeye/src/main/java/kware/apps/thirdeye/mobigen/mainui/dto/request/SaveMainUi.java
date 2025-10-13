package kware.apps.thirdeye.mobigen.mainui.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveMainUi {

    private String code;
    private String name;
    private Integer maxCount;
    private Integer descLineClamp;
    private Integer titleLineClamp;
    private String thumbUseAt;
    private String useAt;
}
