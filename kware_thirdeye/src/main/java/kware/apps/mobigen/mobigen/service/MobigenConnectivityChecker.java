package kware.apps.mobigen.mobigen.service;

import io.netty.channel.ChannelOption;
import io.netty.channel.ConnectTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.annotation.PreDestroy;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 모비젠 서버 접속 여부를 확인해 MobigenAvailability 를 갱신한다.
 * - 기동이 끝나면(ApplicationReadyEvent) 비동기로 한 번 확인하고 바로 지나간다. 기동 시간에 영향을 주지 않는다.
 * - 데몬 스레드 하나가 mobigen.health-check.interval-ms 마다 다시 확인해 끊기면 내리고 복구되면 올린다.
 * - 서버가 어떤 HTTP 응답이든 주면 연결된 것으로 본다(API 의미는 보지 않는다). 연결·응답 타임아웃은 timeout-ms.
 */
@Slf4j
@Component
public class MobigenConnectivityChecker {

    private final MobigenAvailability availability;
    private final WebClient client;
    private final Duration timeout;
    private final boolean checkEnabled;
    private final long intervalMs;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private ScheduledExecutorService scheduler;

    @Autowired
    public MobigenConnectivityChecker(MobigenAvailability availability,
                                      @Value("${mobigen.url}") String baseUrl,
                                      @Value("${mobigen.health-check.enabled:true}") boolean checkEnabled,
                                      @Value("${mobigen.health-check.timeout-ms:2000}") long timeoutMs,
                                      @Value("${mobigen.health-check.interval-ms:60000}") long intervalMs) {
        this(availability, baseUrl, checkEnabled, Duration.ofMillis(timeoutMs), intervalMs);
    }

    MobigenConnectivityChecker(MobigenAvailability availability, String baseUrl, boolean checkEnabled,
                               Duration timeout, long intervalMs) {
        this.availability = availability;
        this.timeout = timeout;
        this.checkEnabled = checkEnabled;
        this.intervalMs = intervalMs;
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) timeout.toMillis())
                        .responseTimeout(timeout)))
                .build();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        if (!availability.isEnabled()) {
            log.info("모비젠 연동이 꺼져 있어(mobigen.enabled=false) 접속 확인을 하지 않는다");
            return;
        }
        if (!checkEnabled) {
            log.info("모비젠 접속 확인이 꺼져 있다(mobigen.health-check.enabled=false). 호출 시점에만 실패를 판단한다");
            return;
        }
        start();
    }

    /** 기동 직후 1회 + interval-ms 주기로 백그라운드 확인을 시작한다 */
    synchronized void start() {
        if (scheduler != null) return;
        scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "mobigen-connectivity");
            t.setDaemon(true);
            return t;
        });
        long period = Math.max(intervalMs, 1000L);
        scheduler.scheduleWithFixedDelay(this::checkNow, 0L, period, TimeUnit.MILLISECONDS);
        log.info("모비젠 접속 확인 시작: 기동 직후 1회, 이후 {}ms 마다 (타임아웃 {}ms)", period, timeout.toMillis());
    }

    @PreDestroy
    public synchronized void stop() {
        if (scheduler != null) {
            scheduler.shutdownNow();
            scheduler = null;
        }
    }

    /** 지금 바로 확인해 상태를 갱신한다. 연결되면 true */
    public boolean checkNow() {
        if (!running.compareAndSet(false, true)) return availability.isAvailable();   // 이미 확인 중이면 중복 실행 안 함
        try {
            Integer status = client.get().uri("")
                    .exchangeToMono(res -> res.releaseBody().thenReturn(res.rawStatusCode()))
                    .timeout(timeout.plusMillis(500))   // 연결 타임아웃이 먼저 걸리게 여유를 둬 사유(연결/응답)를 구분한다
                    .block();
            log.debug("모비젠 접속 확인 응답 HTTP {}", status);
            availability.markUp();
            return true;
        } catch (Exception e) {
            availability.markDown(describe(e));
            return false;
        } finally {
            running.set(false);
        }
    }

    static String describe(Throwable e) {
        Throwable cause = (e.getCause() != null) ? e.getCause() : e;
        if (cause instanceof ConnectTimeoutException) return "연결 시간 초과";   // ConnectException 의 하위라 먼저 본다
        if (cause instanceof ConnectException) return "연결 거부";
        if (cause instanceof UnknownHostException) return "호스트 조회 실패";
        if (cause instanceof TimeoutException) return "응답 시간 초과";
        return cause.getClass().getSimpleName() + (cause.getMessage() != null ? ": " + cause.getMessage() : "");
    }
}
