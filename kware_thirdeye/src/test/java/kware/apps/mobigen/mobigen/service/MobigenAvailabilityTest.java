package kware.apps.mobigen.mobigen.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;

/**
 * 연동 꺼짐·확인 전·연결됨·끊김 네 상태에서 호출 허용 여부가 맞아야 한다.
 */
class MobigenAvailabilityTest {

    private static final Clock FIXED = Clock.fixed(Instant.parse("2026-10-23T09:00:00Z"), ZoneOffset.UTC);

    @Test
    void 연동이_꺼져_있으면_어떤_상태여도_호출하지_않는다() {
        MobigenAvailability a = new MobigenAvailability(false, false, FIXED);
        assertThat(a.isEnabled()).isFalse();
        assertThat(a.isAvailable()).isFalse();
        a.markUp();
        assertThat(a.isAvailable()).isFalse();
        assertThat(a.unavailableMessage()).isEqualTo(MobigenAvailability.DISABLED_MESSAGE);
    }

    @Test
    void 확인_기능이_켜져_있으면_결과가_오기_전에는_호출하지_않는다() {
        MobigenAvailability a = new MobigenAvailability(true, false, FIXED);
        assertThat(a.getState()).isEqualTo(MobigenAvailability.State.UNKNOWN);
        assertThat(a.isAvailable()).isFalse();
        assertThat(a.unavailableMessage()).isEqualTo(MobigenAvailability.UNAVAILABLE_MESSAGE);
    }

    @Test
    void 확인_기능이_꺼져_있으면_결과가_없어도_호출해_본다() {
        MobigenAvailability a = new MobigenAvailability(true, true, FIXED);
        assertThat(a.isAvailable()).isTrue();
        a.markDown("연결 거부");
        assertThat(a.isAvailable()).isFalse();
    }

    @Test
    void 연결_확인_결과에_따라_켜지고_꺼진다() {
        MobigenAvailability a = new MobigenAvailability(true, false, FIXED);

        a.markUp();
        assertThat(a.isAvailable()).isTrue();
        assertThat(a.getState()).isEqualTo(MobigenAvailability.State.UP);
        assertThat(a.getCheckedAt()).isEqualTo(FIXED.instant());
        assertThat(a.getReason()).isNull();

        a.markDown("연결 시간 초과");
        assertThat(a.isAvailable()).isFalse();
        assertThat(a.getState()).isEqualTo(MobigenAvailability.State.DOWN);
        assertThat(a.getReason()).isEqualTo("연결 시간 초과");

        a.markUp();
        assertThat(a.isAvailable()).isTrue();
        assertThat(a.getReason()).isNull();
    }

    @Test
    void 복구_리스너는_UP_으로_바뀔_때만_한_번_불린다() {
        MobigenAvailability a = new MobigenAvailability(true, false, FIXED);
        java.util.concurrent.atomic.AtomicInteger calls = new java.util.concurrent.atomic.AtomicInteger();
        a.addUpListener(calls::incrementAndGet);

        a.markUp();                 // UNKNOWN -> UP
        a.markUp();                 // UP 유지: 호출 없음
        a.markDown("연결 거부");
        a.markUp();                 // DOWN -> UP

        assertThat(calls.get()).isEqualTo(2);
    }

    @Test
    void 리스너_예외는_상태_갱신을_막지_않는다() {
        MobigenAvailability a = new MobigenAvailability(true, false, FIXED);
        a.addUpListener(() -> { throw new IllegalStateException("boom"); });

        a.markUp();

        assertThat(a.getState()).isEqualTo(MobigenAvailability.State.UP);
        assertThat(a.isAvailable()).isTrue();
    }
}
