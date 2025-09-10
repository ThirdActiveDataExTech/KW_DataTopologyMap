package kware.apps.thirdeye.enumstatus;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum UserAuthorCd {

    ROLE_SYSTEM("시스템 관리자"),
    ROLE_ADMIN("데이터 제공자"),
    ROLE_USER("유저 권한");

    private String description;

    UserAuthorCd(String description) {
        this.description = description;
    }

    public static List<EnumCodeDto> toList() {
        return Arrays.stream(values())
                .map(e -> new EnumCodeDto(e.name(), e.getDescription()))
                .collect(Collectors.toList());
    }

    public static String getDescriptionByCode(String code) {
        try {
            return UserAuthorCd.valueOf(code).getDescription();
        } catch (IllegalArgumentException e) {
            return "";
        }
    }

    public static boolean isValidCode(String code) {
        return Arrays.stream(UserAuthorCd.values())
                .anyMatch(e -> e.name().equals(code));
    }
}
