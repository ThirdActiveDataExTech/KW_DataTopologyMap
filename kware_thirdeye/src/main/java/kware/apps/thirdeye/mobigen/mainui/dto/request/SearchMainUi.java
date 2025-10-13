package kware.apps.thirdeye.mobigen.mainui.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchMainUi {

    private String browseText;
    private Long workplaceUid;
    private String useAt;

    public SearchMainUi(String browseText, Long workplaceUid, String useAt) {
        this.browseText = browseText;
        this.workplaceUid = workplaceUid;
        this.useAt = useAt;
    }

    public void setWorkplaceUid(Long workplaceUid) {
        this.workplaceUid = workplaceUid;
    }
}
