package kware.apps.thirdeye.mobigen.category.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteCategories {
    private List<Long> uids;
}
