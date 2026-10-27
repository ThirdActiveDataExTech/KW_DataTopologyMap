package cetus.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import egovframework.rte.fdl.cmmn.trace.LeaveaTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

@Configuration
@PropertySource(value="classpath:application-${spring.profiles.active:local}.yml", factory=YamlPropertyLoaderFactory.class, ignoreResourceNotFound=true)
public class DefaultConfig {
    
    @Bean
    LeaveaTrace leaveaTrace() {
        return new LeaveaTrace();
    }
    
    @Bean
    PathMatcher pathMatcher() {
        return new AntPathMatcher();
    }
    
    /**
     * 이 빈이 Spring Boot 기본 ObjectMapper 를 대체하므로 java.time 모듈을 직접 등록한다.
     * 등록하지 않으면 Instant 등을 가진 응답(JSON)이 변환 오류로 500 이 된다.
     */
    @Bean
    public ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
