package kware.apps.system.statushist.dto.request;


import lombok.Getter;

@Getter
public class UserStatusHistSearch {

    private Long userUid;

    public UserStatusHistSearch(Long userUid) {
        this.userUid = userUid;
    }
}
