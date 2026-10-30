package kware.apps.system.dept.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import cetus.annotation.DisplayName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeptTreeChange {
    @NotBlank @Size(max = 64) @DisplayName("부서 이름")
    private String deptNm;
    private Long sortNo;
}
