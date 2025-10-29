package kware.apps.thirdeye.mobigen.category.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED) @Setter
public class SearchCategory {

    private String categoryNm;
    private Long workplaceUid;

    public SearchCategory(Long workplaceUid) {
        this.workplaceUid = workplaceUid;
    }

    public SearchCategory(String categoryNm, Long workplaceUid) {
        this.categoryNm = categoryNm;
        this.workplaceUid = workplaceUid;
    }

    public void setWorkplaceUid(Long workplaceUid) {
        this.workplaceUid = workplaceUid;
    }
}
