package kware.apps.manager.cetus.user.dto.request;

import cetus.annotation.DisplayName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserChange {

    @Pattern(regexp = "^(?!$).{8,20}$") @DisplayName("비밀번호")
    private String password;

    @NotBlank @DisplayName("사용자 이름")
    private String userNm;
}
