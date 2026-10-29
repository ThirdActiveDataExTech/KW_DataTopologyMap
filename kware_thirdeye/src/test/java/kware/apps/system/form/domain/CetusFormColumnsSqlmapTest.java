package kware.apps.system.form.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

/**
 * 폼 컬럼 매퍼는 사용자 입력을 문자열 치환(${})으로 SQL 에 넣지 않아야 한다.
 * update 의 DEFAULT_VALUE 가 ${defaultValue} 로 들어가 있어 입력값이 SQL 로 실행됐다(2026-10-29 발견).
 * 문자열 치환은 서버가 계산하는 페이지 범위(startNumber·endNumber)만 허용한다.
 */
class CetusFormColumnsSqlmapTest {

    private static final String SQLMAP = "/kware/apps/system/form/domain/cetusFormColumns-postgresql-sqlmap.xml";
    private static final Pattern SUBSTITUTION = Pattern.compile("\\$\\{([^}]*)}");

    @Test
    void 기본값은_바인딩_파라미터로_저장한다() throws IOException {
        String xml = readSqlmap();

        assertThat(xml).doesNotContain("${defaultValue}");
        assertThat(countOf(xml, "#{defaultValue}")).as("insert·update 두 곳").isEqualTo(2);
    }

    @Test
    void 문자열_치환은_페이지_범위만_허용한다() throws IOException {
        List<String> names = substitutedNames(readSqlmap());

        assertThat(names).as("서버가 계산하는 페이지 범위 외의 문자열 치환은 없어야 한다").isSubsetOf("startNumber", "endNumber");
    }

    private static List<String> substitutedNames(String xml) {
        List<String> names = new ArrayList<>();
        Matcher m = SUBSTITUTION.matcher(xml);
        while (m.find()) {
            names.add(m.group(1).trim());
        }
        return names;
    }

    private static int countOf(String text, String needle) {
        int count = 0;
        int idx = -1;
        while ((idx = text.indexOf(needle, idx + 1)) >= 0) {
            count++;
        }
        return count;
    }

    private static String readSqlmap() throws IOException {
        try (InputStream in = CetusFormColumnsSqlmapTest.class.getResourceAsStream(SQLMAP)) {
            assertThat(in).as("매퍼 XML 이 클래스패스에 있어야 한다: " + SQLMAP).isNotNull();
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
