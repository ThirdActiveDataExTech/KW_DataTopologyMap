package kware.apps.manager.cetus.user.dto.request;

import cetus.annotation.YOrN;
import lombok.Getter;

@Getter
public class UserListSearch {

    private String userId;

    public UserListSearch(String userId) {
        this.userId = userId;
    }
}
