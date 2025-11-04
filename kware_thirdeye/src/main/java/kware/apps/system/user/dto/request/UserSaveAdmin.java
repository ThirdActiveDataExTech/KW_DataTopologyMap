package kware.apps.system.user.dto.request;

import cetus.annotation.DisplayName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSaveAdmin {

    @NotBlank
    @DisplayName("사용자ID")
    private String userId;

    @NotBlank @DisplayName("비밀번호")
    private String password;

    @NotBlank @DisplayName("사용자 이름")
    private String userNm;

    @NotBlank @DisplayName("사용자 이메일")
    private String userEmail;

    @NotBlank @DisplayName("권한")
    private String authorCd;

    private Long userDept;
    private Long userGroup;
    private Long userPosition;

    private String status;
    private String metaData;
}
