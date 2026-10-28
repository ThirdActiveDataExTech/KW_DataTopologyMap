package kware.apps.thirdeye.bookmark.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import kware.apps.thirdeye.bookmark.domain.CetusBookMarkDao;
import kware.apps.thirdeye.bookmark.dto.request.SearchUserBookMark;
import kware.apps.thirdeye.bookmark.dto.response.UserBookMarkList;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.ApprovedDatasetItem;
import kware.apps.thirdeye.mobigen.approveddataset.service.CetusApprovedDatasetService;
import org.junit.jupiter.api.Test;

/**
 * 북마크 목록은 북마크 건수와 무관하게 진열 정보를 한 번에 가져와야 한다(행별 상세·모비젠 조회 금지).
 */
class CetusBookMarkServiceTest {

    private final CetusBookMarkDao dao = mock(CetusBookMarkDao.class);
    private final CetusApprovedDatasetService approvedDatasetService = mock(CetusApprovedDatasetService.class);
    private final CetusBookMarkService service = new CetusBookMarkService(dao, approvedDatasetService);

    @Test
    void 북마크가_여러_건이어도_진열_정보는_한_번에_조회한다() throws Exception {
        List<UserBookMarkList> bookmarks = Arrays.asList(bookmark(180L), bookmark(179L), bookmark(20181L));
        when(dao.getUserBookMarkList(any(SearchUserBookMark.class))).thenReturn(bookmarks);
        when(approvedDatasetService.findApprovedDatasetViewList(anyList()))
                .thenReturn(Arrays.asList(item(20181L), item(180L)));   // 179 는 조회 결과 없음(삭제 등)

        List<UserBookMarkList> result = service.findUserBookMarkList(new SearchUserBookMark(206L));

        verify(approvedDatasetService, times(1)).findApprovedDatasetViewList(Arrays.asList(180L, 179L, 20181L));
        verify(approvedDatasetService, never()).findApprovedDatasetView(anyLong());
        assertThat(result).hasSize(3);
        assertThat(result.get(0).getDatasetView().getApprovedUid()).isEqualTo(180L);
        assertThat(result.get(1).getDatasetView()).isNull();
        assertThat(result.get(2).getDatasetView().getApprovedUid()).isEqualTo(20181L);
    }

    @Test
    void 북마크가_없으면_진열_정보를_조회하지_않는다() {
        when(dao.getUserBookMarkList(any(SearchUserBookMark.class))).thenReturn(Collections.emptyList());

        List<UserBookMarkList> result = service.findUserBookMarkList(new SearchUserBookMark(206L));

        assertThat(result).isEmpty();
        verify(approvedDatasetService, never()).findApprovedDatasetViewList(anyList());
        verify(approvedDatasetService, never()).findApprovedDatasetView(anyLong());
    }

    private static UserBookMarkList bookmark(Long approvedUid) throws Exception {
        UserBookMarkList bookmark = newInstance(UserBookMarkList.class);
        set(bookmark, "approvedUid", approvedUid);
        return bookmark;
    }

    private static ApprovedDatasetItem item(Long approvedUid) throws Exception {
        ApprovedDatasetItem item = newInstance(ApprovedDatasetItem.class);
        set(item, "approvedUid", approvedUid);
        return item;
    }

    private static <T> T newInstance(Class<T> type) throws Exception {
        Constructor<T> constructor = type.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    private static void set(Object target, String name, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }
}
