package kware.apps.superrole.workplace.dto.request.createworkplace;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateUser {
    private String id;
    private String name;
    private String email;
    private String password;
    private String role;
}
