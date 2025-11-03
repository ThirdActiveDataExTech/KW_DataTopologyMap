package kware.apps.superrole.workplace.dto.request.createworkplace;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateWorkplace {

    private String workplaceNm;
    private List<CreateUser> users;
    private List<CreateProgram> programs;
    private List<CreateMenu> menus;
}
