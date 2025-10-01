package kware.apps.thirdeye.mainui.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchDuplicateCode {

    private String code;
    private Long workplaceUid;

    public SearchDuplicateCode(String code, Long workplaceUid) {
        this.code = code;
        this.workplaceUid = workplaceUid;
    }
}
