package kware.apps.thirdeye.mobigen.approveddataset.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * 빈 검색어·공백 검색어는 조건을 걸지 않는다(null). 검색어는 앞뒤 공백을 제거한다.
 */
class SearchApprovedDatasetTest {

    @Test
    void 공백만_있는_검색어는_null_이_된다() {
        SearchApprovedDataset search = new SearchApprovedDataset(22L, 1L, null, null, null, null, null, "   ", null);

        assertThat(search.getKeyword()).isNull();
    }

    @Test
    void 빈_문자열_검색어도_null_이_된다() {
        SearchApprovedDataset search = new SearchApprovedDataset(22L, 1L, null, null, null, null, null, "", null);

        assertThat(search.getKeyword()).isNull();
    }

    @Test
    void 검색어_앞뒤_공백은_제거된다() {
        SearchApprovedDataset search = new SearchApprovedDataset(22L, 1L, null, null, null, null, null, "  SBSI ", null);

        assertThat(search.getKeyword()).isEqualTo("SBSI");
    }

    @Test
    void setter_로_넣어도_같은_규칙이_적용된다() {
        SearchApprovedDataset search = new SearchApprovedDataset(22L, 1L, null, null, null, null, null, null, null);

        search.setKeyword(" 세브란스 ");
        assertThat(search.getKeyword()).isEqualTo("세브란스");

        search.setKeyword(" ");
        assertThat(search.getKeyword()).isNull();

        search.setKeyword(null);
        assertThat(search.getKeyword()).isNull();
    }
}
