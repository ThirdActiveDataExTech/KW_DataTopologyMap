package kware.apps.thirdeye.mobigen.category.dto.request.changesortno;


import lombok.AccessLevel;
import cetus.annotation.DisplayName;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeCategorySortNoList {
    @NotEmpty @Valid @DisplayName("카테고리 순서")
    private List<ChangeCategorySortNo> categories;
}
