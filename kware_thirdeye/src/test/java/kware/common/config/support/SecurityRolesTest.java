package kware.common.config.support;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.util.AntPathMatcher;

/**
 * URL 패턴 묶음이 의도한 경로만 잡는지 확인한다. 판정 순서는 WebSecurityConfig.authorizeRequests 와 같다.
 */
class SecurityRolesTest {

    private static final AntPathMatcher MATCHER = new AntPathMatcher();

    enum Group { PERMIT_ALL, SUPER_SYSTEM, SUPER, SYSTEM, ADMIN, USER, AUTHENTICATED }

    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource({
            "/login, PERMIT_ALL",
            "/assets/css/app.css, PERMIT_ALL",
            "/sessionExpired, PERMIT_ALL",
            "/login/force.json, PERMIT_ALL",
            "/thirdeye/main, PERMIT_ALL",
            "/api/thirdeye/data, PERMIT_ALL",
            "/api/tus/file/abc, PERMIT_ALL",
            "/api/portal/files/abc, PERMIT_ALL",
            "/actuator/health, PERMIT_ALL",
            "/actuator/health/liveness, PERMIT_ALL",
            "/actuator, AUTHENTICATED",
            "/actuator/env, AUTHENTICATED",
            "/actuator/healthcheck, AUTHENTICATED",
            "/, AUTHENTICATED",
            "/portal/main, USER",
            "/api/portal/list, USER",
            "/admin/user, ADMIN",
            "/api/admin/user/list, ADMIN",
            "/system/menu, SYSTEM",
            "/api/system/menu, SYSTEM",
            "/super/workplace, SUPER_SYSTEM",
            "/api/super/workplace/list, SUPER_SYSTEM",
            "/super/code/list, SUPER_SYSTEM",
            "/api/super/code/save, SUPER_SYSTEM",
            "/super/user, SUPER",
            "/api/super/user, SUPER",
            "/super/workplace/detail, SUPER"
    })
    void 경로별_권한_묶음(String path, Group expected) {
        assertThat(resolve(path)).isEqualTo(expected);
    }

    private static Group resolve(String path) {
        if (matches(SecurityRoles.PERMIT_ALL_URLS, path)) {
            return Group.PERMIT_ALL;
        }
        if (matches(SecurityRoles.SUPER_SYSTEM_URLS, path)) {
            return Group.SUPER_SYSTEM;
        }
        if (matches(SecurityRoles.SUPER_URLS, path)) {
            return Group.SUPER;
        }
        if (matches(SecurityRoles.SYSTEM_URLS, path)) {
            return Group.SYSTEM;
        }
        if (matches(SecurityRoles.ADMIN_URLS, path)) {
            return Group.ADMIN;
        }
        if (matches(SecurityRoles.USER_URLS, path)) {
            return Group.USER;
        }
        return Group.AUTHENTICATED;
    }

    private static boolean matches(String[] patterns, String path) {
        return Arrays.stream(patterns).anyMatch(pattern -> MATCHER.match(pattern, path));
    }
}
