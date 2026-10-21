package kware.common.config.auth;

import cetus.config.CetusConfig;
import kware.apps.system.loginhist.service.CetusUserLoginHistService;
import kware.apps.system.menu.service.CetusMenuInfoService;
import kware.apps.system.user.service.CetusUserService;
import kware.common.config.IpWhoService;
import kware.common.config.auth.handler.*;
import kware.common.config.auth.menu.MenuManager;
import kware.common.config.support.SecurityRoles;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig {

    private final ApplicationContext applicationContext;
    private static final String LOGIN = "/login";
    private final SessionRegistry sessionRegistry;
    private final CetusConfig config;

    @Bean
    public PrincipalDetailsService principalDetailsService (
            CetusUserService userService,
            CetusMenuInfoService menuService,
            MenuManager menuManager
    ) {
        return new PrincipalDetailsService(userService, menuService, menuManager);
    }

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }

    @Bean
    public MyDaoAuthenticationProvider myDaoAuthenticationProvider(PrincipalDetailsService detailsService, BCryptPasswordEncoder encoder) {
        MyDaoAuthenticationProvider provider = new MyDaoAuthenticationProvider();
        provider.setUserDetailsService(detailsService);
        provider.setPasswordEncoder(encoder);
        return provider;
    }

    @Bean
    public CustomLoginSuccessHandler loginSuccessHandler(CetusUserService userService, CetusUserLoginHistService histService, IpWhoService ipWhoService) {
        return new CustomLoginSuccessHandler(userService, histService, ipWhoService);
    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomLoginFailuerHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(
                HttpSecurity security,
                MyDaoAuthenticationProvider provider,
                CustomLoginSuccessHandler successHandler
    ) throws Exception {

        configureDevOrLocalSecurity(security, provider);

        security
            .authorizeRequests(this::authorizeRequests)
            .exceptionHandling(this::exception)
            .formLogin(formLoginConfigurer -> formLogin(formLoginConfigurer, successHandler))
            .logout(this::logout)
            .sessionManagement(this::sessionManagement);
        return security.build();
    }

    // url 보안 설정
    private void authorizeRequests(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlRegistry) {
        urlRegistry
            .antMatchers(SecurityRoles.PERMIT_ALL_URLS).permitAll()
            .antMatchers(SecurityRoles.SUPER_SYSTEM_URLS).hasAnyAuthority("ROLE_SYSTEM", "ROLE_SUPER")
            .antMatchers(SecurityRoles.SUPER_URLS).hasAuthority("ROLE_SUPER")
            .antMatchers(SecurityRoles.SYSTEM_URLS).hasAnyAuthority("ROLE_SYSTEM", "ROLE_SUPER")
            .antMatchers(SecurityRoles.ADMIN_URLS).hasAnyAuthority("ROLE_ADMIN", "ROLE_SYSTEM", "ROLE_SUPER")
            .antMatchers(SecurityRoles.USER_URLS).hasAnyAuthority("ROLE_USER", "ROLE_ADMIN", "ROLE_SYSTEM", "ROLE_SUPER")
            .anyRequest().authenticated();
    }

    // 보안 설정
    private void configureDevOrLocalSecurity(
                    HttpSecurity httpSecurity,
                    MyDaoAuthenticationProvider myDaoAuthenticationProvider
    ) throws Exception {
        if(isLocalProfile()) {
            httpSecurity
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(AbstractHttpConfigurer::disable)
                    .headers(this::headers)
                    .authenticationProvider(myDaoAuthenticationProvider);
        } else {
            httpSecurity
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
                    .headers(this::headers)
                    .authenticationProvider(myDaoAuthenticationProvider);
        }
    }

    // 응답 보안 헤더. X-Content-Type-Options: nosniff 와 Cache-Control 은 Spring Security 기본값으로 이미 붙는다.
    // CSP 는 화면의 인라인 스크립트·외부 CDN 영향을 보기 위해 report-only 로 먼저 적용한다(차단하지 않고 브라우저 콘솔에만 위반 표시).
    private void headers(HeadersConfigurer<HttpSecurity> headers) {
        headers
            .frameOptions(frameOptions -> frameOptions.sameOrigin())   // iframe 허용 (same origin만)
            .xssProtection(xss -> xss.xssProtectionEnabled(true))
            .referrerPolicy(referrer -> referrer.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
            .contentSecurityPolicy(csp -> csp.policyDirectives(CSP_REPORT_ONLY).reportOnly());
    }

    private static final String CSP_REPORT_ONLY = String.join("; ",
            "default-src 'self'",
            "script-src 'self' 'unsafe-inline' 'unsafe-eval' https://cdn.jsdelivr.net https://cdn.ckeditor.com",
            "style-src 'self' 'unsafe-inline' https://fonts.googleapis.com https://cdn.jsdelivr.net",
            "font-src 'self' data: https://fonts.gstatic.com https://cdn.jsdelivr.net",
            "img-src 'self' data: blob:",
            "connect-src 'self'",
            "frame-ancestors 'self'");

    // 권한 페이지 설정
    private void exception(ExceptionHandlingConfigurer<HttpSecurity> exceptionHandling) {
        exceptionHandling
                .authenticationEntryPoint(new CustomAuthEntryPoint(LOGIN))
                .accessDeniedHandler(new CustomAccessDeniedHandler());
    }

    // formLogin 설정
    private void formLogin(FormLoginConfigurer<HttpSecurity> formLogin, CustomLoginSuccessHandler loginSuccessHandler)  {
        formLogin
            .loginPage(LOGIN) // 로그인 페이지 링크
            .usernameParameter("userId")
            .loginProcessingUrl("/loginProc")
            .successHandler(loginSuccessHandler)
            .failureHandler(customAuthenticationFailureHandler());
    }

    // logout 설정
    private void logout(LogoutConfigurer<HttpSecurity> logout) {
        logout
            .logoutUrl("/logout")
            .logoutSuccessUrl(LOGIN)        // 로그아웃 성공시 연결되는 주소
            .invalidateHttpSession(true)    // 로그아웃시 저장해 둔 세션 제거
            .clearAuthentication(true);
    }

    // 중복 로그인 설정
    private void sessionManagement(SessionManagementConfigurer<HttpSecurity> manage) {
        manage
            .sessionFixation().changeSessionId()
            .maximumSessions(config.getMaxSession())
            .maxSessionsPreventsLogin(true)
            .sessionRegistry(sessionRegistry)
            .expiredUrl(LOGIN);
    }

    // cors 설정
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        String allowedOrigin = allowedOrigins();
        if (allowedOrigin.isBlank()) {
            // 허용 목록이 없으면 자격증명(쿠키) 없는 교차 출처 요청만 허용한다.
            // 와일드카드 출처 + allowCredentials(true) 조합은 어떤 사이트든 세션 쿠키를 싣고 호출할 수 있게 한다.
            config.addAllowedOriginPattern("*");
            config.setAllowCredentials(false);
        } else {
            for (String origin : allowedOrigin.split(",")) {
                if (!origin.isBlank()) config.addAllowedOrigin(origin.trim());
            }
            config.setAllowCredentials(true);
        }
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    private String allowedOrigins() {
        return config.getAllowedOrigin() == null ? "" : config.getAllowedOrigin();
    }

    private boolean isLocalProfile() {
        List<String> profiles = Arrays.asList(applicationContext.getEnvironment().getActiveProfiles());
        return profiles.contains("local");
    }
}
