package kware.apps.mobigen.mobigen.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.time.Clock;
import java.time.Duration;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClientRequestException;

/**
 * 서버가 어떤 HTTP 응답이든 주면 UP, 연결이 안 되면 DOWN 으로 기록해야 한다. 기동 시 확인은 비동기라 기동을 막지 않는다.
 */
class MobigenConnectivityCheckerTest {

    private static MobigenAvailability availability() {
        return new MobigenAvailability(true, false, Clock.systemUTC());
    }

    @Test
    void 서버가_404라도_응답하면_연결된_것으로_본다() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/", ex -> { ex.sendResponseHeaders(404, -1); ex.close(); });
        server.start();
        try {
            MobigenAvailability a = availability();
            MobigenConnectivityChecker checker = new MobigenConnectivityChecker(
                    a, "http://127.0.0.1:" + server.getAddress().getPort() + "/v1", true, Duration.ofSeconds(2), 60000);

            assertThat(checker.checkNow()).isTrue();
            assertThat(a.isAvailable()).isTrue();
            assertThat(a.getState()).isEqualTo(MobigenAvailability.State.UP);
        } finally {
            server.stop(0);
        }
    }

    @Test
    void 포트가_닫혀_있으면_연결_불가로_기록한다() throws IOException {
        int closedPort;
        try (ServerSocket s = new ServerSocket(0, 1, java.net.InetAddress.getLoopbackAddress())) {
            closedPort = s.getLocalPort();
        }
        MobigenAvailability a = availability();
        MobigenConnectivityChecker checker = new MobigenConnectivityChecker(
                a, "http://127.0.0.1:" + closedPort + "/v1", true, Duration.ofSeconds(2), 60000);

        assertThat(checker.checkNow()).isFalse();
        assertThat(a.isAvailable()).isFalse();
        assertThat(a.getState()).isEqualTo(MobigenAvailability.State.DOWN);
        assertThat(a.getReason()).isNotBlank();
    }

    @Test
    void 기동_이벤트는_바로_돌아오고_확인은_백그라운드에서_끝난다() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/", ex -> { ex.sendResponseHeaders(200, -1); ex.close(); });
        server.start();
        MobigenAvailability a = availability();
        MobigenConnectivityChecker checker = new MobigenConnectivityChecker(
                a, "http://127.0.0.1:" + server.getAddress().getPort() + "/v1", true, Duration.ofSeconds(2), 60000);
        try {
            long started = System.nanoTime();
            checker.onApplicationReady();
            long elapsedMs = (System.nanoTime() - started) / 1_000_000;
            assertThat(elapsedMs).isLessThan(1000);

            long deadline = System.currentTimeMillis() + 5000;
            while (!a.isAvailable() && System.currentTimeMillis() < deadline) Thread.sleep(20);
            assertThat(a.isAvailable()).isTrue();
        } finally {
            checker.stop();
            server.stop(0);
        }
    }

    @Test
    void 연동이_꺼져_있으면_기동_이벤트에서_확인을_시작하지_않는다() {
        MobigenAvailability a = new MobigenAvailability(false, false, Clock.systemUTC());
        MobigenConnectivityChecker checker = new MobigenConnectivityChecker(a, "http://127.0.0.1:1/v1", true, Duration.ofSeconds(1), 60000);
        checker.onApplicationReady();
        assertThat(a.getState()).isEqualTo(MobigenAvailability.State.UNKNOWN);
        checker.stop();
    }

    @Test
    void 실패_원인을_짧은_문구로_바꾼다() {
        assertThat(MobigenConnectivityChecker.describe(wrap(new ConnectException("refused")))).isEqualTo("연결 거부");
        assertThat(MobigenConnectivityChecker.describe(wrap(new io.netty.channel.ConnectTimeoutException("t")))).isEqualTo("연결 시간 초과");
        assertThat(MobigenConnectivityChecker.describe(wrap(new UnknownHostException("h")))).isEqualTo("호스트 조회 실패");
        assertThat(MobigenConnectivityChecker.describe(new TimeoutException("x"))).isEqualTo("응답 시간 초과");
        assertThat(MobigenConnectivityChecker.describe(new IllegalStateException("odd"))).isEqualTo("IllegalStateException: odd");
    }

    private static WebClientRequestException wrap(Throwable cause) {
        return new WebClientRequestException(cause, org.springframework.http.HttpMethod.GET,
                java.net.URI.create("http://mobigen.test/"), new org.springframework.http.HttpHeaders());
    }
}
