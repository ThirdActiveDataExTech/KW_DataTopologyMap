package kware.common.config.session;

import static org.assertj.core.api.Assertions.assertThat;

import kware.common.config.session.springsession.SpringSessionConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.web.http.SessionRepositoryFilter;

/**
 * spring.session.store-type(SESSION_STORE_TYPE) 값에 따라 메모리 세션 구성이 켜지고 꺼져야 한다.
 * redis 구성은 기동 때 Redis 에 접속하므로 여기서는 none 쪽만 띄우고, redis 값일 때 메모리 구성이 빠지는지만 본다.
 */
class SessionStoreConfigTest {

    private final WebApplicationContextRunner runner = new WebApplicationContextRunner()
            .withUserConfiguration(SpringSessionConfig.class);

    @Test
    void none이면_메모리_세션_저장소와_세션_레지스트리를_만든다() {
        runner.withPropertyValues("spring.session.store-type=none").run(context -> {
            assertThat(context).hasSingleBean(SessionRepository.class);
            assertThat(context.getBean(SessionRepository.class)).isInstanceOf(MapSessionRepository.class);
            assertThat(context).hasSingleBean(SessionRegistry.class);
            assertThat(context.getBean(SessionRegistry.class)).isInstanceOf(SessionRegistryImpl.class);
            assertThat(context).hasSingleBean(SessionRepositoryFilter.class);
        });
    }

    @Test
    void redis면_메모리_세션_구성은_빠진다() {
        runner.withPropertyValues("spring.session.store-type=redis").run(context -> {
            assertThat(context).doesNotHaveBean(SpringSessionConfig.class);
            assertThat(context).doesNotHaveBean(SessionRepository.class);
            assertThat(context).doesNotHaveBean(SessionRegistry.class);
        });
    }

    @Test
    void 값이_없으면_어느_쪽도_켜지지_않는다() {
        runner.run(context -> {
            assertThat(context).doesNotHaveBean(SpringSessionConfig.class);
            assertThat(context).doesNotHaveBean(SessionRepository.class);
        });
    }
}
