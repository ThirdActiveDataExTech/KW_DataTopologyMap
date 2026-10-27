package kware.apps.thirdeye.mobigen.approveddataset.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * 진열 데이터셋 목록 SQL 의 승인일 기간 조건 검증.
 * 종료일 조건이 "종료일 다음 날 00:00 미만" 이어야 하는데 부호가 반대(초과)로 들어가면
 * 기간 안이 아니라 기간 밖(종료일 이후) 데이터만 조회되는 결함이 생긴다(2026-10-25 발견).
 */
class CetusApprovedDatasetSqlmapTest {

    private static final String SQLMAP = "/kware/apps/thirdeye/mobigen/approveddataset/domain/cetusApprovedDataset-postgresql-sqlmap.xml";
    private static final String END_LT = "CAD.APPROVED_DT <![CDATA[<]]> (#{endDate}::DATE + INTERVAL '1 day')";
    private static final String END_GT = "CAD.APPROVED_DT <![CDATA[>]]> (#{endDate}::DATE + INTERVAL '1 day')";
    private static final String START_GE = "CAD.APPROVED_DT >= #{startDate}::TIMESTAMP";

    @Test
    void 종료일_조건은_다음날_미만이어야_한다() throws IOException {
        String xml = readSqlmap();

        assertThat(countOf(xml, END_GT)).as("종료일 초과 조건(결함)은 없어야 한다").isZero();
        assertThat(countOf(xml, END_LT)).as("목록(getDatasetPage)·건수(getDatasetPageCount) 두 곳").isEqualTo(2);
    }

    @Test
    void 시작일_조건은_이상이어야_한다() throws IOException {
        String xml = readSqlmap();

        assertThat(countOf(xml, START_GE)).isEqualTo(2);
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
        try (InputStream in = CetusApprovedDatasetSqlmapTest.class.getResourceAsStream(SQLMAP)) {
            assertThat(in).as("매퍼 XML 이 클래스패스에 있어야 한다: " + SQLMAP).isNotNull();
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
