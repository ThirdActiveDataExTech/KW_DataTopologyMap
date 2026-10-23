package kware.common.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

/**
 * 정적 리소스 위치는 설정 경로가 상대 경로여도 절대 file: URI 가 되어야 하고, 디렉터리이므로 / 로 끝나야 한다.
 */
class WebConfigTest {

    @Test
    void 상대_경로는_작업_디렉터리_기준_절대_URI가_된다() {
        String location = WebConfig.directoryLocation("./local-data/ckImage/");

        String expectedPrefix = Paths.get("").toAbsolutePath().toUri().toString();
        assertThat(location).startsWith("file:");
        assertThat(location).startsWith(expectedPrefix);
        assertThat(location).endsWith("/local-data/ckImage/");
        assertThat(location).doesNotContain("/./");
    }

    @Test
    void 절대_경로는_그대로_URI가_된다() {
        String absolute = Paths.get("").toAbsolutePath().resolve("some-dir").toString();

        String location = WebConfig.directoryLocation(absolute);

        assertThat(location).isEqualTo(Paths.get(absolute).toUri().toString().replaceAll("/?$", "/"));
    }

    @Test
    void 끝에_슬래시가_없어도_붙인다() {
        assertThat(WebConfig.directoryLocation("./local-data/ckImage")).endsWith("/ckImage/");
        assertThat(WebConfig.directoryLocation("./local-data/ckImage/")).endsWith("/ckImage/");
        assertThat(WebConfig.directoryLocation("./local-data/ckImage/")).doesNotEndWith("//");
    }
}
