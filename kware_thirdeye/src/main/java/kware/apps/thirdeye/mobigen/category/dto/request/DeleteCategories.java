package kware.apps.thirdeye.mobigen.category.dto.request;


import lombok.AccessLevel;
import cetus.annotation.DisplayName;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteCategories {
    @NotEmpty @DisplayName("삭제 대상")
    private List<Long> uids;
}
