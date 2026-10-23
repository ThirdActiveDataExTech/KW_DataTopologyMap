package kware.apps.mobigen.mobigen.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.net.ServerSocket;
import java.time.Clock;
import kware.apps.mobigen.mobigen.dto.request.metadata.DownloadMetadataFileRequest;
import kware.apps.mobigen.mobigen.dto.response.ApiResponse;
import kware.apps.mobigen.mobigen.dto.response.meta.MetaKeysListResponse;
import kware.common.exception.SimpleException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

/**
 * 미연결·연동 꺼짐 상태에서는 서버를 부르지 않고 즉시 실패해야 하고, 연결 실패를 겪으면 상태를 내려 다음 호출은 즉시 실패해야 한다.
 */
class MobigenExternalApiServiceGuardTest {

    private static int closedPort() throws IOException {
        try (ServerSocket s = new ServerSocket(0, 1, java.net.InetAddress.getLoopbackAddress())) {
            return s.getLocalPort();
        }
    }

    private static MobigenExternalApiService service(MobigenAvailability a, int port) {
        return new MobigenExternalApiService("http://127.0.0.1:" + port + "/v1", 300, 300, 1, 0, a);
    }

    @Test
    void 연동이_꺼져_있으면_호출_없이_꺼짐_메시지를_돌려준다() throws IOException {
        MobigenAvailability a = new MobigenAvailability(false, false, Clock.systemUTC());
        long started = System.nanoTime();

        ApiResponse<MetaKeysListResponse> res = service(a, closedPort()).findMetaKeysList();

        assertThat(res.isSuccess()).isFalse();
        assertThat(res.getMessage()).isEqualTo(MobigenAvailability.DISABLED_MESSAGE);
        assertThat((System.nanoTime() - started) / 1_000_000).isLessThan(200);
    }

    @Test
    void 미연결로_기록돼_있으면_즉시_실패한다() throws IOException {
        MobigenAvailability a = new MobigenAvailability(true, false, Clock.systemUTC());
        a.markDown("연결 거부");

        ApiResponse<MetaKeysListResponse> res = service(a, closedPort()).findMetaKeysList();

        assertThat(res.isSuccess()).isFalse();
        assertThat(res.getMessage()).isEqualTo(MobigenAvailability.UNAVAILABLE_MESSAGE);
    }

    @Test
    void 다운로드는_미연결이면_503으로_실패한다() throws IOException {
        MobigenAvailability a = new MobigenAvailability(true, false, Clock.systemUTC());
        a.markDown("연결 거부");
        MobigenExternalApiService svc = service(a, closedPort());

        assertThatThrownBy(() -> svc.downloadMetadataFile(new DownloadMetadataFileRequest()))
                .isInstanceOf(SimpleException.class)
                .satisfies(e -> assertThat(((SimpleException) e).getStatus()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE));
    }

    @Test
    void 연결_실패를_겪으면_상태를_내려_다음_호출은_서버를_부르지_않는다() throws IOException {
        MobigenAvailability a = new MobigenAvailability(true, true, Clock.systemUTC());   // 확인 기능 꺼짐: 일단 호출해 본다
        MobigenExternalApiService svc = service(a, closedPort());

        ApiResponse<MetaKeysListResponse> first = svc.findMetaKeysList();
        assertThat(first.isSuccess()).isFalse();
        assertThat(a.getState()).isEqualTo(MobigenAvailability.State.DOWN);

        long started = System.nanoTime();
        ApiResponse<MetaKeysListResponse> second = svc.findMetaKeysList();
        assertThat(second.getMessage()).isEqualTo(MobigenAvailability.UNAVAILABLE_MESSAGE);
        assertThat((System.nanoTime() - started) / 1_000_000).isLessThan(200);
    }
}
