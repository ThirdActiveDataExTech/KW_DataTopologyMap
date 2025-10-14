package kware.apps.thirdeye.mobigen.category.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class SearchUsingCategory {

    private Long mainUiUid;
    private Long workplaceUid;
    private Integer limit;

    public SearchUsingCategory(Long mainUiUid, Long workplaceUid, Integer limit) {
        this.mainUiUid = mainUiUid;
        this.workplaceUid = workplaceUid;
        this.limit = limit;
    }

    public void setWorkplaceUid(Long workplaceUid) {
        this.workplaceUid = workplaceUid;
    }
}
