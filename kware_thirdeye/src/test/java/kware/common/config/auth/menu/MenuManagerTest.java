package kware.common.config.auth.menu;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import kware.apps.system.menu.domain.CetusMenu;
import kware.apps.system.menu.dto.response.SessionMenuList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.test.util.ReflectionTestUtils;

class MenuManagerTest {

    private final MenuManager menuManager = new MenuManager();

    @Test
    void 루트_행이_있으면_그_아래_1단_메뉴를_정렬번호_순으로_반환한다() {
        List<SessionMenuList> rows = Arrays.asList(
                menu(1L, null, "root", 0, null),
                menu(10L, 1L, "둘째", 2, "/b"),
                menu(11L, 1L, "첫째", 1, "/a"),
                menu(100L, 10L, "둘째-1", 1, "/b/1"),
                menu(101L, 10L, "둘째-2", 2, "/b/2"));

        List<CetusMenu> tree = menuManager.parseInfoListToTreeList(rows, 999L);

        assertThat(tree).extracting(CetusMenu::getMenuNm).containsExactly("첫째", "둘째");
        CetusMenu second = tree.get(1);
        assertThat(second.hasChild()).isTrue();
        assertThat(second.getChildren()).extracting(CetusMenu::getMenuNo).containsExactly(100L, 101L);
        assertThat(tree.get(0).hasChild()).isFalse();
    }

    @Test
    void 루트_행이_없으면_인자로_받은_루트_번호_아래_메뉴를_반환한다() {
        List<SessionMenuList> rows = Arrays.asList(
                menu(10L, 5L, "A", 1, "/a"),
                menu(11L, 5L, "B", 2, "/b"),
                menu(12L, 10L, "A-1", 1, "/a/1"));

        List<CetusMenu> tree = menuManager.parseInfoListToTreeList(rows, 5L);

        assertThat(tree).extracting(CetusMenu::getMenuNo).containsExactly(10L, 11L);
        assertThat(tree.get(0).getChildren()).extracting(CetusMenu::getMenuNo).containsExactly(12L);
    }

    @Test
    void 루트_아래_메뉴가_없으면_빈_목록을_반환한다() {
        List<SessionMenuList> rows = Arrays.asList(menu(1L, null, "root", 0, null));

        assertThat(menuManager.parseInfoListToTreeList(rows, 1L)).isEmpty();
    }

    @Test
    void 슬래시_하나인_url은_링크_없음으로_바꾼다() {
        List<SessionMenuList> rows = Arrays.asList(
                menu(1L, null, "root", 0, null),
                menu(10L, 1L, "홈", 1, "/"),
                menu(11L, 1L, "목록", 2, "/list"),
                menu(12L, 1L, "없음", 3, null));

        List<CetusMenu> tree = menuManager.parseInfoListToTreeList(rows, 1L);

        assertThat(tree).extracting(CetusMenu::getUrl).containsExactly("#", "/list", "");
    }

    @Test
    void 정렬번호가_없는_메뉴는_맨_앞에_두고_트리_구성을_계속한다() {
        List<SessionMenuList> rows = Arrays.asList(
                menu(1L, null, "root", 0, null),
                menu(10L, 1L, "번호있음", 1, "/a"),
                menu(11L, 1L, "번호없음", null, "/b"));

        List<CetusMenu> tree = menuManager.parseInfoListToTreeList(rows, 1L);

        assertThat(tree).extracting(CetusMenu::getMenuNm).containsExactly("번호없음", "번호있음");
        assertThat(tree.get(0).getSortNo()).isEqualTo(-1);
    }

    private static SessionMenuList menu(Long menuNo, Long upperMenuNo, String menuNm, Integer sortNo, String url) {
        SessionMenuList row = BeanUtils.instantiateClass(SessionMenuList.class);
        ReflectionTestUtils.setField(row, "menuNo", menuNo);
        ReflectionTestUtils.setField(row, "upperMenuNo", upperMenuNo);
        ReflectionTestUtils.setField(row, "menuNm", menuNm);
        ReflectionTestUtils.setField(row, "sortNo", sortNo);
        ReflectionTestUtils.setField(row, "url", url);
        return row;
    }
}
