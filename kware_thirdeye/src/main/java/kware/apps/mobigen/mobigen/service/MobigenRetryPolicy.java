package kware.apps.mobigen.mobigen.service;

import io.netty.channel.ConnectTimeoutException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.util.retry.Retry;

/**
 * 모비젠 호출 재시도 기준. 서버에 요청이 닿기 전의 실패(연결 거부·연결 타임아웃·호스트 조회 실패)만 재시도한다.
 * 읽기 타임아웃·HTTP 오류 응답은 서버가 이미 처리했을 수 있어 재시도하지 않는다(중복 생성 방지).
 */
@Slf4j
final class MobigenRetryPolicy {

    private MobigenRetryPolicy() {
    }

    static Retry connectRetry(int maxAttempts, Duration firstBackoff) {
        return Retry.backoff(maxAttempts, firstBackoff)
                .filter(MobigenRetryPolicy::isConnectFailure)
                .doBeforeRetry(signal -> log.warn("[RETRY] 모비젠 연결 실패, {}회째 재시도: {}", signal.totalRetries() + 1, signal.failure().getMessage()))
                .onRetryExhaustedThrow((spec, signal) -> signal.failure());
    }

    static boolean isConnectFailure(Throwable t) {
        if (!(t instanceof WebClientRequestException)) return false;
        Throwable cause = t.getCause();
        return cause instanceof ConnectException
                || cause instanceof ConnectTimeoutException
                || cause instanceof UnknownHostException;
    }
}
