package kware.common.config.support;

public class SecurityRoles {

    public static String[] PERMIT_ALL_URLS = {
            "/login",

            "/assets/**",
            "/sessionExpired",
            "/login/force.json",

            "/api/thirdeye/**",
            "/thirdeye/**",

            "/invite/validate",

            "/api/tus/file/**",
            "/api/cetus/files/**"
    };

    public static final String[] USER_URLS = {
            "/portal/**",
            "/api/portal/**"
    };

    public static final String[] SYSTEM_URLS = {
            "/system/**",
            "/api/system/**"
    };

    public static final String[] ADMIN_URLS = {
            "/admin/**",
            "/api/admin/**"
    };
}


