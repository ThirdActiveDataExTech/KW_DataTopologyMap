package kware.apps.system.user.dto.request;


import cetus.annotation.DisplayName;
import cetus.annotation.ValidPassword;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserChangeMyInfo {

    private String userNm;

    private String userEmail;

    private String metaData;

    @ValidPassword(allowNull = true)
    private String password;
}
