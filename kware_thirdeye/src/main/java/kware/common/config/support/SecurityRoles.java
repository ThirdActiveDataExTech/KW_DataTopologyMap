package kware.common.config.support;

public class SecurityRoles {

    public static String[] PERMIT_ALL_URLS = {
            "/login",

            "/assets/**",
            "/sessionExpired",
            "/login/force.json",

            "/asp/signup",
            "/cetus/api/user/checkEmail.json",
            "/cetus/api/user/checkId.json",
            "/cetus/api/user",

            "/invite/validate",

            "/expired",
            "/signup",

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


