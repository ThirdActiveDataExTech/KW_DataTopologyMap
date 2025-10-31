package kware.apps.thirdeye.enumstatus;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum UserAuthorCd {

    ROLE_SUPER("슈퍼 관리자", false),
    ROLE_SYSTEM("시스템 관리자", true),
    ROLE_ADMIN("데이터 제공자", true),
    ROLE_USER("유저 권한", true);

    private String description;
    private boolean useForService;

    UserAuthorCd(String description, boolean useForService) {
        this.description = description;
        this.useForService = useForService;
    }


    public static List<EnumCodeDto> toList() {
        return Arrays.stream(values())
                .filter(UserAuthorCd::isUseForService)  // useForService == true인 것만
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
