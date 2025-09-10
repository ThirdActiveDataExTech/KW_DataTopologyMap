package kware.common.config;

import cetus.config.CetusConfig;
import cetus.log.LoggingInterceptor;
import cetus.menu.MenuInterceptor;
import cetus.user.UserInterceptor;
import kware.common.validator.ValidMessageInterpolator;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.resource.VersionResourceResolver;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final CetusConfig configs;
    private final LocaleChangeInterceptor localeChangeInterceptor;
    private final LoggingInterceptor loggingInterceptor;
    private final UserInterceptor userInterceptor;
    private final MenuInterceptor menuInterceptor;

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setMessageInterpolator(new ValidMessageInterpolator());
        return validatorFactoryBean;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/ckImage/**")
                .addResourceLocations("file:///" + configs.getCkeditorPath());

        registry.addResourceHandler("/assets/**")
                .addResourceLocations("classpath:/static/assets/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS))
                .resourceChain(true)
                .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        loggingInterceptor.setLogger(LoggerFactory.getLogger(LoggingInterceptor.class));
        loggingInterceptor.setLogging(configs.getLogging());
        registry.addInterceptor(loggingInterceptor).excludePathPatterns("/assets/**/*", "/swagger-ui/**", "/swagger-resources/**", "/api/cetus/files/view/**");
        registry.addInterceptor(userInterceptor).excludePathPatterns("/assets/**/*", "/login", "/loginProc", "/logout", "/error/**/*");
        registry.addInterceptor(localeChangeInterceptor);
    }

}
