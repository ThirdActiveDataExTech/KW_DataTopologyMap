package kware.apps.thirdeye.mobigen.category.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchPageCategory {

    private String browseText;
    private Long workplaceUid;
    private String useAt;

    public SearchPageCategory(String browseText, Long workplaceUid, String useAt) {
        this.browseText = browseText;
        this.workplaceUid = workplaceUid;
        this.useAt = useAt;
    }

    public void setWorkplaceUid(Long workplaceUid) {
        this.workplaceUid = workplaceUid;
    }
}
