package kware.apps.thirdeye.mobigen.approveddataset.service;

import kware.apps.mobigen.integration.dto.request.metadata.SearchMetadataView;
import kware.apps.mobigen.integration.dto.response.metadata.MetadataView;
import kware.apps.mobigen.integration.service.DatasetService;
import kware.apps.mobigen.mobigen.service.MobigenAvailability;
import kware.apps.thirdeye.mobigen.approveddataset.domain.CetusApprovedDatasetDao;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.ApprovedDatasetItem;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.TitleBackfillResult;
import kware.apps.thirdeye.mobigen.datasetui.domain.CetusDatasetUiDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Clock;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 진열 데이터셋의 제목을 extra_json 에 채운다(백필).
 * - 제목은 원래 모비젠에만 있어 목록 검색·표시에 쓸 수 없었다. 진열 등록 때 저장하도록 바꾼 뒤에도
 *   그 전에 등록된 행은 비어 있으므로, 모비젠이 연결돼 있을 때 한 번씩 받아와 채운다.
 * - 기동 직후(연결돼 있으면)와 모비젠이 DOWN→UP 으로 복구될 때 백그라운드 스레드에서 자동 실행된다.
 * - 관리자 API 로 수동 실행할 수도 있다. 실행 중 모비젠이 끊기면 그 자리에서 멈추고 다음 복구 때 이어서 한다.
 */
@Slf4j
@Service
public class ApprovedDatasetTitleBackfillService {

    private final CetusApprovedDatasetDao approvedDatasetDao;
    private final CetusDatasetUiDao datasetUiDao;
    private final DatasetService datasetService;
    private final MobigenAvailability availability;
    private final Clock clock;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private volatile TitleBackfillResult lastResult;
    private ExecutorService executor;

    @Autowired
    public ApprovedDatasetTitleBackfillService(CetusApprovedDatasetDao approvedDatasetDao, CetusDatasetUiDao datasetUiDao,
                                               DatasetService datasetService, MobigenAvailability availability) {
        this(approvedDatasetDao, datasetUiDao, datasetService, availability, Clock.systemDefaultZone());
    }

    ApprovedDatasetTitleBackfillService(CetusApprovedDatasetDao approvedDatasetDao, CetusDatasetUiDao datasetUiDao,
                                        DatasetService datasetService, MobigenAvailability availability, Clock clock) {
        this.approvedDatasetDao = approvedDatasetDao;
        this.datasetUiDao = datasetUiDao;
        this.datasetService = datasetService;
        this.availability = availability;
        this.clock = clock;
    }

    @PostConstruct
    void registerRecoveryHook() {
        availability.addUpListener(this::runAsync);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        if (availability.isAvailable()) runAsync();
    }

    /** 백그라운드 스레드에서 실행한다(기동·접속 확인 스레드를 막지 않는다) */
    public synchronized void runAsync() {
        if (executor == null) {
            executor = Executors.newSingleThreadExecutor(r -> {
                Thread t = new Thread(r, "approved-title-backfill");
                t.setDaemon(true);
                return t;
            });
        }
        executor.submit(() -> {
            try {
                backfill();
            } catch (RuntimeException e) {
                log.warn("진열 데이터셋 제목 백필 실패: {}", e.toString());
            }
        });
    }

    @PreDestroy
    synchronized void stop() {
        if (executor != null) {
            executor.shutdownNow();
            executor = null;
        }
    }

    /** 제목이 비어 있는 진열 데이터셋에 모비젠 제목을 채운다. 동기 실행(관리자 API·테스트용) */
    public TitleBackfillResult backfill() {
        if (!running.compareAndSet(false, true)) return record(TitleBackfillResult.alreadyRunning(clock.instant()));
        try {
            if (!availability.isAvailable()) {
                return record(TitleBackfillResult.skipped(availability.unavailableMessage(), clock.instant()));
            }
            List<ApprovedDatasetItem> targets = approvedDatasetDao.getTitleMissingList();
            if (targets == null || targets.isEmpty()) {
                return record(new TitleBackfillResult(TitleBackfillResult.Status.DONE, 0, 0, 0, "채울 대상이 없습니다.", clock.instant()));
            }
            log.info("진열 데이터셋 제목 백필 시작: 대상 {}건", targets.size());
            int updated = 0, failed = 0, seen = 0;
            for (ApprovedDatasetItem target : targets) {
                if (!availability.isAvailable()) {
                    log.warn("제목 백필 중단: 모비젠 연결 끊김 (처리 {}/{}건)", seen, targets.size());
                    return record(new TitleBackfillResult(TitleBackfillResult.Status.STOPPED, targets.size(), updated, failed,
                            "모비젠 연결이 끊겨 중단했습니다. 복구되면 다시 실행됩니다.", clock.instant()));
                }
                seen++;
                String title = fetchTitle(target.getMetadataId());
                if (title == null) {
                    failed++;
                    continue;
                }
                datasetUiDao.updateExtraJsonTitle(target.getApprovedUid(), title);
                updated++;
            }
            log.info("진열 데이터셋 제목 백필 완료: 대상 {}건, 채움 {}건, 실패 {}건", targets.size(), updated, failed);
            return record(new TitleBackfillResult(TitleBackfillResult.Status.DONE, targets.size(), updated, failed, null, clock.instant()));
        } finally {
            running.set(false);
        }
    }

    public TitleBackfillResult getLastResult() {
        return lastResult;
    }

    private TitleBackfillResult record(TitleBackfillResult result) {
        lastResult = result;
        return result;
    }

    /** 모비젠 상세에서 제목만 꺼낸다. 응답이 없거나 제목이 비면 null */
    String fetchTitle(String metadataId) {
        try {
            MetadataView view = datasetService.viewMetadata(new SearchMetadataView(metadataId));
            if (view == null || view.getMetadataResponse() == null) return null;
            String title = view.getMetadataResponse().getTitle();
            return (title == null || title.trim().isEmpty()) ? null : title.trim();
        } catch (RuntimeException e) {
            log.debug("제목 조회 실패 metadataId={}: {}", metadataId, e.toString());
            return null;
        }
    }
}
