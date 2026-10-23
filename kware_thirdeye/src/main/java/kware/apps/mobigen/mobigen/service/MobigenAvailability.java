package kware.apps.mobigen.mobigen.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;

/**
 * 모비젠 연동 가능 여부를 한 곳에서 기억한다.
 * - mobigen.enabled=false 면 아예 호출하지 않는다.
 * - 접속 확인(MobigenConnectivityChecker) 결과로 UP/DOWN 이 바뀌고, DOWN 이면 외부 API 호출을 즉시 실패시켜
 *   화면이 연결 타임아웃·재시도를 기다리지 않게 한다.
 * - 확인 기능이 켜져 있는데 아직 결과가 없으면(UNKNOWN) 안전하게 "불가"로 본다. 확인 기능이 꺼져 있으면 호출해 본다.
 */
@Slf4j
@Component
public class MobigenAvailability {

    public enum State { UNKNOWN, UP, DOWN }

    public static final String DISABLED_MESSAGE = "모비젠 연동이 설정(mobigen.enabled)에서 꺼져 있습니다.";
    public static final String UNAVAILABLE_MESSAGE = "모비젠 연동 서버에 연결할 수 없습니다.";

    private final boolean enabled;
    private final boolean unknownMeansAvailable;
    private final Clock clock;

    private volatile State state = State.UNKNOWN;
    private volatile Instant checkedAt;
    private volatile String reason;

    @Autowired
    public MobigenAvailability(@Value("${mobigen.enabled:true}") boolean enabled,
                               @Value("${mobigen.health-check.enabled:true}") boolean healthCheckEnabled) {
        this(enabled, !healthCheckEnabled, Clock.systemDefaultZone());
    }

    MobigenAvailability(boolean enabled, boolean unknownMeansAvailable, Clock clock) {
        this.enabled = enabled;
        this.unknownMeansAvailable = unknownMeansAvailable;
        this.clock = clock;
    }

    public boolean isEnabled() {
        return enabled;
    }

    /** 지금 모비젠 API 를 호출해도 되는지 */
    public boolean isAvailable() {
        if (!enabled) return false;
        if (state == State.UP) return true;
        return state == State.UNKNOWN && unknownMeansAvailable;
    }

    public void markUp() {
        State before = state;
        state = State.UP;
        checkedAt = clock.instant();
        reason = null;
        if (before != State.UP) log.info("모비젠 연동 서버 연결 확인: 사용 가능 (이전 상태 {})", before);
    }

    public void markDown(String why) {
        State before = state;
        state = State.DOWN;
        checkedAt = clock.instant();
        reason = why;
        if (before != State.DOWN) log.warn("모비젠 연동 서버 연결 불가: {} (이전 상태 {}). 복구될 때까지 모비젠 호출을 즉시 실패 처리한다", why, before);
        else log.debug("모비젠 연동 서버 여전히 연결 불가: {}", why);
    }

    public State getState() {
        return state;
    }

    public Instant getCheckedAt() {
        return checkedAt;
    }

    public String getReason() {
        return reason;
    }

    /** 호출을 막았을 때 사용자에게 돌려줄 문구 */
    public String unavailableMessage() {
        return enabled ? UNAVAILABLE_MESSAGE : DISABLED_MESSAGE;
    }
}
