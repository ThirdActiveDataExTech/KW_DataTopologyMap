package kware.apps.system.dept.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import cetus.annotation.DisplayName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeptTreeSave {
    @NotBlank @Size(max = 64) @DisplayName("부서 이름")
    private String deptNm;
    private Long sortNo;
    private Long upperUid;
    @NotNull @DisplayName("워크플레이스")
    private Long workplaceUid;
}
