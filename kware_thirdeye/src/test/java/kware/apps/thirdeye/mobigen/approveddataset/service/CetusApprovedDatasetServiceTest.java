package kware.apps.thirdeye.mobigen.approveddataset.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kware.apps.mobigen.integration.service.DatasetService;
import kware.apps.thirdeye.mobigen.approveddataset.domain.CetusApprovedDatasetDao;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.ApprovedDatasetItem;
import kware.apps.thirdeye.mobigen.category.service.CetusDatasetCategoryService;
import kware.apps.thirdeye.mobigen.datasetui.dto.response.DatasetUiView;
import kware.apps.thirdeye.mobigen.datasetui.service.CetusDatasetUiService;
import kware.apps.thirdeye.mobigen.mainui.dto.response.MainUiView;
import kware.apps.thirdeye.mobigen.mainui.service.CetusDatasetMainUiService;
import kware.apps.thirdeye.bookmark.service.CetusBookMarkService2;
import org.junit.jupiter.api.Test;

/**
 * 목록·홈의 진열 정보 채우기는 페이지당 진열 1회·메인 UI 1회 조회로 끝나야 한다(행별 조회 금지).
 */
class CetusApprovedDatasetServiceTest {

    private final CetusDatasetUiService datasetUiService = mock(CetusDatasetUiService.class);
    private final CetusDatasetMainUiService mainUiService = mock(CetusDatasetMainUiService.class);
    private final CetusApprovedDatasetService service = new CetusApprovedDatasetService(
            mock(CetusApprovedDatasetDao.class), mock(CetusApprovedDatasetService2.class),
            datasetUiService, mainUiService, mock(CetusDatasetCategoryService.class),
            mock(CetusBookMarkService2.class), mock(DatasetService.class));

    @Test
    void 페이지의_진열_정보와_메인UI_를_각각_한_번씩만_조회한다() throws Exception {
        List<ApprovedDatasetItem> items = Arrays.asList(item(1L), item(2L), item(3L));
        Map<Long, DatasetUiView> uiMap = new HashMap<>();
        uiMap.put(1L, ui(1L, 28L));
        uiMap.put(2L, ui(2L, 28L));
        uiMap.put(3L, ui(3L, 29L));
        when(datasetUiService.findDatasetUiViewMap(anyList())).thenReturn(uiMap);
        Map<Long, MainUiView> mainMap = new HashMap<>();
        mainMap.put(28L, mainUi(28L));
        mainMap.put(29L, mainUi(29L));
        when(mainUiService.findDatasetMainUiMap(anyList())).thenReturn(mainMap);

        service.settingLocalDatasetInfo(items);

        verify(datasetUiService, times(1)).findDatasetUiViewMap(Arrays.asList(1L, 2L, 3L));
        verify(mainUiService, times(1)).findDatasetMainUiMap(anyList());
        verify(datasetUiService, never()).findDatasetUiView(1L);
        assertThat(items.get(0).getDatasetUi().getMainUiUid()).isEqualTo(28L);
        assertThat(items.get(0).getMainUi().getUid()).isEqualTo(28L);
        assertThat(items.get(2).getMainUi().getUid()).isEqualTo(29L);
    }

    @Test
    void 진열_정보가_없는_행은_비워_두고_나머지는_채운다() throws Exception {
        List<ApprovedDatasetItem> items = Arrays.asList(item(1L), item(9L));
        when(datasetUiService.findDatasetUiViewMap(anyList())).thenReturn(Collections.singletonMap(1L, ui(1L, 28L)));
        when(mainUiService.findDatasetMainUiMap(anyList())).thenReturn(Collections.singletonMap(28L, mainUi(28L)));

        service.settingLocalDatasetInfo(items);

        assertThat(items.get(0).getMainUi()).isNotNull();
        assertThat(items.get(1).getDatasetUi()).isNull();
        assertThat(items.get(1).getMainUi()).isNull();
    }

    @Test
    void 빈_목록이면_조회하지_않는다() {
        service.settingLocalDatasetInfo(Collections.emptyList());
        service.settingLocalDatasetInfo(null);

        verify(datasetUiService, never()).findDatasetUiViewMap(anyList());
        verify(mainUiService, never()).findDatasetMainUiMap(anyList());
    }

    private static ApprovedDatasetItem item(Long approvedUid) throws Exception {
        ApprovedDatasetItem item = newInstance(ApprovedDatasetItem.class);
        set(item, "approvedUid", approvedUid);
        return item;
    }

    private static DatasetUiView ui(Long approvedUid, Long mainUiUid) throws Exception {
        DatasetUiView view = newInstance(DatasetUiView.class);
        set(view, "approvedDatasetUid", approvedUid);
        set(view, "mainUiUid", mainUiUid);
        return view;
    }

    private static MainUiView mainUi(Long uid) throws Exception {
        MainUiView view = newInstance(MainUiView.class);
        set(view, "uid", uid);
        return view;
    }

    /** 응답 DTO 는 기본 생성자가 protected 라 리플렉션으로 만든다 */
    private static <T> T newInstance(Class<T> type) throws Exception {
        Constructor<T> c = type.getDeclaredConstructor();
        c.setAccessible(true);
        return c.newInstance();
    }

    private static void set(Object target, String field, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(field);
        f.setAccessible(true);
        f.set(target, value);
    }
}
