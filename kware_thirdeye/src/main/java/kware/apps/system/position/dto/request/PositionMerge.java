package kware.apps.system.position.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import cetus.annotation.DisplayName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PositionMerge {

    private Long uid;
    @NotBlank @Size(max = 64) @DisplayName("직위 이름")
    private String positionNm;
    private Long sortNo;
}
