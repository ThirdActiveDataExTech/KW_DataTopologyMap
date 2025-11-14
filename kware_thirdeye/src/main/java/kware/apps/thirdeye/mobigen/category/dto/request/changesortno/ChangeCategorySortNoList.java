package kware.apps.thirdeye.mobigen.category.dto.request.changesortno;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeCategorySortNoList {
    private List<ChangeCategorySortNo> categories;
}
