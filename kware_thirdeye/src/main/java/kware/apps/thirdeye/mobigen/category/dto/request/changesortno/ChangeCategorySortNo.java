package kware.apps.thirdeye.mobigen.category.dto.request.changesortno;


import lombok.AccessLevel;
import cetus.annotation.DisplayName;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeCategorySortNo {
    @NotNull @DisplayName("카테고리")
    private Long categoryUid;
    @NotNull @Min(1) @DisplayName("정렬 순서")
    private Integer sortNo;         // 화면 입력 min=1
}
