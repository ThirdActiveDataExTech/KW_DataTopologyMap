package kware.apps.mobigen.mobigen.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

/**
 * 연결 자체가 안 된 경우만 재시도하고, 서버가 받았을 수 있는 실패(읽기 타임아웃·HTTP 오류)는 바로 실패해야 한다.
 */
class MobigenRetryPolicyTest {

    private static final Duration FAST = Duration.ofMillis(1);

    @Test
    void 연결_거부_연결_타임아웃_호스트_조회_실패만_연결_실패다() {
        assertThat(MobigenRetryPolicy.isConnectFailure(requestFailure(new ConnectException("refused")))).isTrue();
        assertThat(MobigenRetryPolicy.isConnectFailure(requestFailure(new io.netty.channel.ConnectTimeoutException("timeout")))).isTrue();
        assertThat(MobigenRetryPolicy.isConnectFailure(requestFailure(new UnknownHostException("no-such-host")))).isTrue();

        assertThat(MobigenRetryPolicy.isConnectFailure(requestFailure(new IOException("reset")))).isFalse();
        assertThat(MobigenRetryPolicy.isConnectFailure(requestFailure(new TimeoutException("read")))).isFalse();
        assertThat(MobigenRetryPolicy.isConnectFailure(responseFailure(HttpStatus.INTERNAL_SERVER_ERROR))).isFalse();
        assertThat(MobigenRetryPolicy.isConnectFailure(new ConnectException("bare"))).isFalse();
        assertThat(MobigenRetryPolicy.isConnectFailure(new RuntimeException("other"))).isFalse();
    }

    @Test
    void 연결_실패는_최대_횟수까지_재시도한_뒤_원래_예외를_던진다() {
        AtomicInteger calls = new AtomicInteger();
        WebClientRequestException failure = requestFailure(new ConnectException("refused"));

        assertThatThrownBy(() -> Mono.fromCallable(() -> {
                    calls.incrementAndGet();
                    throw failure;
                })
                .retryWhen(MobigenRetryPolicy.connectRetry(2, FAST))
                .block())
                .isSameAs(failure);

        assertThat(calls.get()).isEqualTo(3);   // 최초 1회 + 재시도 2회
    }

    @Test
    void 재시도_중_성공하면_그_결과를_돌려준다() {
        AtomicInteger calls = new AtomicInteger();

        String result = Mono.fromCallable(() -> {
                    if (calls.incrementAndGet() < 3) {
                        throw requestFailure(new UnknownHostException("flaky"));
                    }
                    return "ok";
                })
                .retryWhen(MobigenRetryPolicy.connectRetry(2, FAST))
                .block();

        assertThat(result).isEqualTo("ok");
        assertThat(calls.get()).isEqualTo(3);
    }

    @Test
    void HTTP_오류_응답은_재시도하지_않는다() {
        AtomicInteger calls = new AtomicInteger();
        WebClientResponseException failure = responseFailure(HttpStatus.BAD_GATEWAY);

        assertThatThrownBy(() -> Mono.fromCallable(() -> {
                    calls.incrementAndGet();
                    throw failure;
                })
                .retryWhen(MobigenRetryPolicy.connectRetry(2, FAST))
                .block())
                .isSameAs(failure);

        assertThat(calls.get()).isEqualTo(1);
    }

    @Test
    void 읽기_타임아웃은_재시도하지_않는다() {
        AtomicInteger calls = new AtomicInteger();
        WebClientRequestException failure = requestFailure(new TimeoutException("read timeout"));

        assertThatThrownBy(() -> Mono.fromCallable(() -> {
                    calls.incrementAndGet();
                    throw failure;
                })
                .retryWhen(MobigenRetryPolicy.connectRetry(2, FAST))
                .block())
                .isSameAs(failure);

        assertThat(calls.get()).isEqualTo(1);
    }

    private static WebClientRequestException requestFailure(Throwable cause) {
        return new WebClientRequestException(cause, HttpMethod.POST, URI.create("http://mobigen.test/api"), new HttpHeaders());
    }

    private static WebClientResponseException responseFailure(HttpStatus status) {
        return WebClientResponseException.create(status.value(), status.getReasonPhrase(), new HttpHeaders(),
                "{}".getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }
}
