package kware.apps.superrole.workplace.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import cetus.annotation.DisplayName;

import javax.validation.constraints.NotNull;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetWorkplace {
    @NotNull @DisplayName("워크플레이스")
    private Long workplaceUid;
    private String workplaceNm;
}
