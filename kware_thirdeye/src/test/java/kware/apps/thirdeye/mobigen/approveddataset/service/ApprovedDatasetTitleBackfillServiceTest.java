package kware.apps.thirdeye.mobigen.approveddataset.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import kware.apps.mobigen.integration.dto.request.metadata.SearchMetadataView;
import kware.apps.mobigen.integration.dto.response.metadata.MetadataView;
import kware.apps.mobigen.integration.service.DatasetService;
import kware.apps.mobigen.mobigen.dto.response.metadata.ViewMetadataResponse;
import kware.apps.mobigen.mobigen.service.MobigenAvailability;
import kware.apps.thirdeye.mobigen.approveddataset.domain.CetusApprovedDatasetDao;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.ApprovedDatasetItem;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.TitleBackfillResult;
import kware.apps.thirdeye.mobigen.datasetui.domain.CetusDatasetUiDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 제목 백필은 모비젠이 연결돼 있을 때만 돌고, 제목을 받은 행만 갱신하며, 도중에 끊기면 멈춰야 한다.
 */
class ApprovedDatasetTitleBackfillServiceTest {

    private static final Clock FIXED = Clock.fixed(Instant.parse("2026-10-24T08:00:00Z"), ZoneOffset.UTC);

    private CetusApprovedDatasetDao approvedDao;
    private CetusDatasetUiDao uiDao;
    private DatasetService datasetService;

    @BeforeEach
    void setUp() {
        approvedDao = mock(CetusApprovedDatasetDao.class);
        uiDao = mock(CetusDatasetUiDao.class);
        datasetService = mock(DatasetService.class);
    }

    private ApprovedDatasetTitleBackfillService service(MobigenAvailability availability) {
        return new ApprovedDatasetTitleBackfillService(approvedDao, uiDao, datasetService, availability, FIXED);
    }

    private static ApprovedDatasetItem target(long uid, String metadataId) throws Exception {
        java.lang.reflect.Constructor<ApprovedDatasetItem> c = ApprovedDatasetItem.class.getDeclaredConstructor();
        c.setAccessible(true);
        ApprovedDatasetItem item = c.newInstance();
        set(item, "approvedUid", uid);
        set(item, "metadataId", metadataId);
        return item;
    }

    private static void set(Object o, String field, Object value) throws Exception {
        java.lang.reflect.Field f = o.getClass().getDeclaredField(field);
        f.setAccessible(true);
        f.set(o, value);
    }

    private static MetadataView viewWithTitle(String title) {
        ViewMetadataResponse response = new ViewMetadataResponse();
        response.setTitle(title);
        return new MetadataView(response, "any");
    }

    @Test
    void 모비젠이_연결되지_않으면_조회도_하지_않고_건너뛴다() {
        MobigenAvailability availability = new MobigenAvailability(true, true);   // 접속 확인 켬: UNKNOWN = 불가
        TitleBackfillResult result = service(availability).backfill();

        assertThat(result.getStatus()).isEqualTo(TitleBackfillResult.Status.SKIPPED);
        assertThat(result.getMessage()).isEqualTo(MobigenAvailability.UNAVAILABLE_MESSAGE);
        verify(approvedDao, never()).getTitleMissingList();
    }

    @Test
    void 제목을_받은_행만_갱신하고_못_받은_행은_실패로_센다() throws Exception {
        MobigenAvailability availability = new MobigenAvailability(true, true);
        availability.markUp();
        when(approvedDao.getTitleMissingList()).thenReturn(Arrays.asList(target(1L, "M-1"), target(2L, "M-2"), target(3L, "M-3")));
        when(datasetService.viewMetadata(any(SearchMetadataView.class))).thenAnswer(inv -> {
            String id = ((SearchMetadataView) inv.getArgument(0)).getMetadataId();
            if ("M-1".equals(id)) return viewWithTitle("  병원 진료 통계  ");
            if ("M-2".equals(id)) return null;                 // 모비젠 응답 실패
            return viewWithTitle("   ");                        // 제목 비어 있음
        });

        TitleBackfillResult result = service(availability).backfill();

        assertThat(result.getStatus()).isEqualTo(TitleBackfillResult.Status.DONE);
        assertThat(result.getTargets()).isEqualTo(3);
        assertThat(result.getUpdated()).isEqualTo(1);
        assertThat(result.getFailed()).isEqualTo(2);
        verify(uiDao).updateExtraJsonTitle(eq(1L), eq("병원 진료 통계"));
        verify(uiDao, never()).updateExtraJsonTitle(eq(2L), anyString());
        verify(uiDao, never()).updateExtraJsonTitle(eq(3L), anyString());
    }

    @Test
    void 실행_도중_모비젠이_끊기면_그_자리에서_멈춘다() throws Exception {
        MobigenAvailability availability = new MobigenAvailability(true, true);
        availability.markUp();
        when(approvedDao.getTitleMissingList()).thenReturn(Arrays.asList(target(1L, "M-1"), target(2L, "M-2")));
        when(datasetService.viewMetadata(any(SearchMetadataView.class))).thenAnswer(inv -> {
            availability.markDown("연결 거부");               // 첫 조회 뒤 끊김
            return viewWithTitle("첫 번째");
        });

        TitleBackfillResult result = service(availability).backfill();

        assertThat(result.getStatus()).isEqualTo(TitleBackfillResult.Status.STOPPED);
        assertThat(result.getUpdated()).isEqualTo(1);
        verify(uiDao).updateExtraJsonTitle(eq(1L), eq("첫 번째"));
        verify(uiDao, never()).updateExtraJsonTitle(eq(2L), anyString());
    }

    @Test
    void 대상이_없으면_모비젠을_부르지_않는다() {
        MobigenAvailability availability = new MobigenAvailability(true, true);
        availability.markUp();
        when(approvedDao.getTitleMissingList()).thenReturn(Collections.<ApprovedDatasetItem>emptyList());

        TitleBackfillResult result = service(availability).backfill();

        assertThat(result.getStatus()).isEqualTo(TitleBackfillResult.Status.DONE);
        assertThat(result.getTargets()).isZero();
        verify(datasetService, never()).viewMetadata(any());
    }

    @Test
    void 모비젠_조회_예외는_그_행만_실패로_처리한다() throws Exception {
        MobigenAvailability availability = new MobigenAvailability(true, true);
        availability.markUp();
        when(approvedDao.getTitleMissingList()).thenReturn(Collections.singletonList(target(7L, "M-7")));
        when(datasetService.viewMetadata(any(SearchMetadataView.class))).thenThrow(new IllegalStateException("boom"));

        TitleBackfillResult result = service(availability).backfill();

        assertThat(result.getStatus()).isEqualTo(TitleBackfillResult.Status.DONE);
        assertThat(result.getFailed()).isEqualTo(1);
        verify(uiDao, never()).updateExtraJsonTitle(any(), anyString());
    }

    @Test
    void 복구_리스너로_등록되어_UP_전환_때_실행된다() throws Exception {
        MobigenAvailability availability = new MobigenAvailability(true, true);
        when(approvedDao.getTitleMissingList()).thenReturn(Collections.<ApprovedDatasetItem>emptyList());
        ApprovedDatasetTitleBackfillService svc = service(availability);
        svc.registerRecoveryHook();

        availability.markUp();

        // 백그라운드 스레드가 끝날 때까지 잠시 대기
        long deadline = System.currentTimeMillis() + 3000;
        while (svc.getLastResult() == null && System.currentTimeMillis() < deadline) Thread.sleep(20);
        assertThat(svc.getLastResult()).isNotNull();
        assertThat(svc.getLastResult().getStatus()).isEqualTo(TitleBackfillResult.Status.DONE);
        svc.stop();
    }
}
