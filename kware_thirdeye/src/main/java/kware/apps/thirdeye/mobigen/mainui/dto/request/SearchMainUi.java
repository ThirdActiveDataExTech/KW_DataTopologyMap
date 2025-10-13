package kware.apps.thirdeye.mobigen.mainui.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchMainUi {

    private String browseText;
    private Long workplaceUid;

    public SearchMainUi(String browseText, Long workplaceUid) {
        this.browseText = browseText;
        this.workplaceUid = workplaceUid;
    }

    public void setWorkplaceUid(Long workplaceUid) {
        this.workplaceUid = workplaceUid;
    }
}
