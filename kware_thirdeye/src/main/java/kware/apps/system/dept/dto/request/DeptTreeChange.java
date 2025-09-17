package kware.apps.system.dept.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeptTreeChange {
    private String deptNm;
    private Long sortNo;
}
