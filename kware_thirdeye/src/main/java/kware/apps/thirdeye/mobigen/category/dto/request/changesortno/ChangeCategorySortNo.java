package kware.apps.thirdeye.mobigen.category.dto.request.changesortno;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeCategorySortNo {
    private Long categoryUid;
    private Integer sortNo;
}
