package kware.apps.system.workplace.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import cetus.annotation.DisplayName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkplaceSave {

    @NotBlank @Size(max = 64) @DisplayName("워크플레이스 이름")
    private String workplaceNm;
}
