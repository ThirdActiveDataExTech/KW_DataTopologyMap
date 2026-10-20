package kware.common.config.support;

public class SecurityRoles {

    public static final String[] PERMIT_ALL_URLS = {
            "/login",

            "/assets/**",
            "/sessionExpired",
            "/login/force.json",

            "/api/thirdeye/**",
            "/thirdeye/**",

            "/api/tus/file/**",
            "/api/portal/files/**",

            // k8s 프로브·모니터링용. health 외 actuator 엔드포인트는 노출하지 않는다(application.yml).
            "/actuator/health",
            "/actuator/health/**"
    };

    public static final String[] USER_URLS = {
            "/portal/**",
            "/api/portal/**"
    };

    public static final String[] ADMIN_URLS = {
            "/admin/**",
            "/api/admin/**"
    };

    public static final String[] SYSTEM_URLS = {
            "/system/**",
            "/api/system/**"
    };

    public static final String[] SUPER_SYSTEM_URLS = {
            "/super/workplace",
            "/api/super/workplace/**",
            "/super/code/**",
            "/api/super/code/**"

    };

    public static final String[] SUPER_URLS = {
            "/super/**",
            "/api/super/**"
    };
}


