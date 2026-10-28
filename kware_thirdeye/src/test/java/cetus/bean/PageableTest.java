package cetus.bean;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * 요청 파라미터로 바인딩되는 페이지 크기·번호는 범위를 벗어나면 잘라낸다. 코드에서 생성자로 넣는 값은 그대로 둔다.
 */
class PageableTest {

    @Test
    void 요청_건수가_상한을_넘으면_상한으로_줄인다() {
        Pageable pageable = new Pageable();

        pageable.setSize(100000);

        assertThat(pageable.getSize()).isEqualTo(Pageable.MAX_SIZE);
        assertThat(pageable.generateMap(new Object()).get("endNumber")).isEqualTo(Pageable.MAX_SIZE);
    }

    @Test
    void 요청_건수가_0_이하면_1_로_올린다() {
        Pageable pageable = new Pageable();

        pageable.setSize(0);
        assertThat(pageable.getSize()).isEqualTo(1);

        pageable.setSize(-5);
        assertThat(pageable.getSize()).isEqualTo(1);
    }

    @Test
    void 페이지_번호가_1_보다_작으면_1_로_올리고_offset_은_0_이_된다() {
        Pageable pageable = new Pageable();

        pageable.setPageNumber(-3);
        pageable.setCountPage(0);

        assertThat(pageable.getPageNumber()).isEqualTo(1);
        assertThat(pageable.getCountPage()).isEqualTo(1);
        Map<String, Object> map = pageable.generateMap(new Object());
        assertThat(map.get("startNumber")).isEqualTo(0);
    }

    @Test
    void 범위_안의_값과_null_은_그대로_둔다() {
        Pageable pageable = new Pageable();

        pageable.setSize(25);
        pageable.setPageNumber(3);
        pageable.setSize(null);
        pageable.setPageNumber(null);

        assertThat(pageable.getSize()).isEqualTo(25);
        assertThat(pageable.getPageNumber()).isEqualTo(3);
        assertThat(pageable.generateMap(new Object()).get("startNumber")).isEqualTo(50);
    }

    @Test
    void 코드에서_생성자로_넣은_큰_값은_제한하지_않는다() {
        Pageable pageable = new Pageable(1000);   // 엑셀 내보내기 등 내부 일괄 조회

        assertThat(pageable.getSize()).isEqualTo(1000);
    }
}
