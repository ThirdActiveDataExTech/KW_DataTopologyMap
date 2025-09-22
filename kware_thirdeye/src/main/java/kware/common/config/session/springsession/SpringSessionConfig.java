package kware.common.config.session.springsession;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.session.MapSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableSpringHttpSession
@ConditionalOnProperty(name = "spring.session.store-type", havingValue = "none")
public class SpringSessionConfig {

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public SessionRepository<MapSession> sessionRepository() {
        MapSessionRepository sessionRepository = new MapSessionRepository(new ConcurrentHashMap<>());
        sessionRepository.setDefaultMaxInactiveInterval(86400);     // 마지막 요청 시점부터 카운트 -> 1일
        return sessionRepository;
    }

}
