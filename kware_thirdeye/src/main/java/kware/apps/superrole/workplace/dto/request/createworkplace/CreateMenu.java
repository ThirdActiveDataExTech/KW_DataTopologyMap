package kware.apps.superrole.workplace.dto.request.createworkplace;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateMenu {
    private Long menuNo;
    private Long upperMenuNo;
    private Long programUid;
    private Integer sortNo;
    private String menuNm;
    private String rootMenuCd;
    private List<CreateMenu> children;
}
