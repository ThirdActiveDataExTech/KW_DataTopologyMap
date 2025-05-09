package kware.apps.manager.cetus.user.dto.request;

import cetus.annotation.DisplayName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSave {

    @NotBlank @DisplayName("사용자ID")
    private String userId;

    @NotBlank @DisplayName("비밀번호")
    private String password;

    @NotBlank @DisplayName("사용자 이름")
    private String userNm;
}
