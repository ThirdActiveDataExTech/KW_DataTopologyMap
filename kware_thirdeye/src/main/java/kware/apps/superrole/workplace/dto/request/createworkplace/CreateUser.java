package kware.apps.superrole.workplace.dto.request.createworkplace;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import cetus.annotation.DisplayName;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateUser {
    @NotBlank @Size(max = 32) @DisplayName("아이디")
    private String id;
    @NotBlank @Size(max = 32) @DisplayName("이름")
    private String name;
    @NotBlank @Email @Size(max = 32) @DisplayName("이메일")
    private String email;
    @NotBlank @DisplayName("비밀번호")
    private String password;
    @NotBlank @DisplayName("권한")
    private String role;
}
