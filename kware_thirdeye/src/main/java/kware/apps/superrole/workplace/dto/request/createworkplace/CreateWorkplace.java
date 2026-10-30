package kware.apps.superrole.workplace.dto.request.createworkplace;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import cetus.annotation.DisplayName;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateWorkplace {

    @NotBlank @Size(max = 64) @DisplayName("워크플레이스 이름")
    private String workplaceNm;
    @NotNull @Valid @DisplayName("사용자 목록")
    private List<CreateUser> users;
    @NotNull @DisplayName("프로그램 목록")
    private List<CreateProgram> programs;
    @NotNull @DisplayName("시스템 메뉴")
    private List<CreateMenu> systemMenus;
    @NotNull @DisplayName("관리자 메뉴")
    private List<CreateMenu> adminMenus;
    @NotNull @DisplayName("사용자 메뉴")
    private List<CreateMenu> userMenus;
}
