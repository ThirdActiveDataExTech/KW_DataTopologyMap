package kware.common.config;

import cetus.log.LoggingInterceptor;
import cetus.menu.MenuInterceptor;
import cetus.user.UserInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;

@Configuration
@RequiredArgsConstructor
public class AuthConfig {

    @Bean
    public LoggingInterceptor loggingInterceptor() {
        return new LoggingInterceptor();
    }

    @Bean
    public UserInterceptor userInterceptor() {
        return new UserInterceptor();
    }

    @Bean
    public MenuInterceptor menuInterceptor() {
        return new MenuInterceptor();
    }

    @Bean
    public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
        return new ResourceUrlEncodingFilter();
    }
}
