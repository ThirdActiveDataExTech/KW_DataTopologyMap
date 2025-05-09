package kware.apps.manager.cetus.enumstatus;

import lombok.Getter;

@Getter
public enum UserAuthorCd {

    SUPER("슈퍼 권한"),
    SYSTEM("시스템 권한"),
    MANAGER("운영자 권한"),
    USER("유저 권한");

    private String name;

    UserAuthorCd(String name) {
        this.name = name;
    }
}
