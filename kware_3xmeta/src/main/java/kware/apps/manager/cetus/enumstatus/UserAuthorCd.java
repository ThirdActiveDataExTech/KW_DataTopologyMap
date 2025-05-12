package kware.apps.manager.cetus.enumstatus;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<EnumCodeDto> toList() {
        return Arrays.stream(values())
                .map(e -> new EnumCodeDto(e.name(), e.getName()))
                .collect(Collectors.toList());
    }

    public static boolean isValidCode(String code) {
        return Arrays.stream(UserAuthorCd.values())
                .anyMatch(status -> status.name().equals(code));
    }
}
