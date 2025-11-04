package kware.apps.system.user.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserChangeMyInfo {

    private String userNm;

    private String userEmail;

    private String metaData;

    private String password;
}
