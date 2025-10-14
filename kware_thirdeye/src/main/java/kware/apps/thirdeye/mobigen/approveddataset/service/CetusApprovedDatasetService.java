package kware.apps.thirdeye.mobigen.approveddataset.service;

import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.user.UserUtil;
import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetView;
import kware.apps.mobigen.cetus.dataset.service.CetusMobigenDatasetService;
import kware.apps.thirdeye.mobigen.approveddataset.domain.CetusApprovedDataset;
import kware.apps.thirdeye.mobigen.approveddataset.domain.CetusApprovedDatasetDao;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.ApprovedDatasetSearch;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.DeleteApprovedDatasets;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.HomeDatasetSearch;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.SaveApprovedDataset;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.ApprovedDatasetIdList;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.ApprovedDatasetView;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.DatasetList;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.HomeDatasetList;
import kware.apps.thirdeye.mobigen.category.service.CetusDatasetCategoryService;
import kware.apps.thirdeye.mobigen.datasetui.dto.response.DatasetUiView;
import kware.apps.thirdeye.mobigen.datasetui.service.CetusDatasetUiService;
import kware.apps.thirdeye.mobigen.mainui.domain.DatasetMainUiType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CetusApprovedDatasetService {

    private final CetusApprovedDatasetDao dao;
    private final CetusDatasetUiService datasetUiService;
    private final CetusMobigenDatasetService mobigenDatasetService;

    private final CetusDatasetCategoryService datasetCategoryService;

    /**
     * @method      findDatasetPage
     * @author      dahyeon
     * @date        2025-10-01
     * @deacription [KWARE] 승인된 데이터셋 목록 조회 + 페이징
     *                  => 각 승인된 datasetId 값을 가지고 모비젠 측의 API를 통해 데이터셋 상세 정보 조회
    **/
    @Transactional(readOnly = true)
    public Page<DatasetList> findDatasetPage(ApprovedDatasetSearch search, Pageable pageable ) {
        log.info(">>> [KWARE] 승인된 데이터셋 목록 조회 + 페이징");
        search.setWorkplaceUid(UserUtil.getUserWorkplaceUid());
        Page<DatasetList> page = dao.page("getDatasetPage", "getDatasetPageCount", search, pageable);
        if(page.getList() != null && !page.getList().isEmpty()) {
            page.getList().forEach(dataset -> {
                // 데이터셋 정보 조회
                Long datasetId = dataset.getDatasetId();
                MobigenDatasetView datasetView = mobigenDatasetService.findMobigenDatasetByDatasetId(datasetId);
                if (datasetView != null) {
                    dataset.setDatasetInfo(datasetView);
                }
                // 데이터셋의 ui 정보
                DatasetMainUiType mainUiType = DatasetMainUiType.valueOf(dataset.getMainUiTypeCd());
                dataset.setMainUiTypeCdDescription(mainUiType.getDescription());
            });
        }
        return page;
    }

    /**
     * @method      findDatasetList
     * @author      dahyeon
     * @date        2025-10-01
     * @deacription [KWARE] 승인된 데이터셋 목록 조회 (리스트)
     *                  => 각 승인된 datasetId 값을 가지고 모비젠 측의 API를 통해 데이터셋 상세 정보 조회
     **/
    @Transactional(readOnly = true)
    public List<DatasetList> findDatasetList( ApprovedDatasetSearch search ) {
        log.info(">>> [KWARE] 승인된 데이터셋 목록 조회 (리스트)");
        search.setWorkplaceUid(UserUtil.getUserWorkplaceUid());
        List<DatasetList> list = dao.getDatasetList(search);

        List<Long> exceptUids = search.getExceptUids(); // 제외할 UID 목록
        if(exceptUids != null && !exceptUids.isEmpty()) {
            list = list.stream()
                    .filter(dataset -> !exceptUids.contains(dataset.getDatasetId()))
                    .collect(Collectors.toList());
        }
        if(!list.isEmpty()) {
            list.forEach(dataset -> {
                Long datasetId = dataset.getDatasetId();
                MobigenDatasetView datasetView = mobigenDatasetService.findMobigenDatasetByDatasetId(datasetId);
                if (datasetView != null) {
                    dataset.setDatasetInfo(datasetView);
                }
            });
        }
        return list;
    }

    /**
     * @method      approveDataset
     * @author      dahyeon
     * @date        2025-10-01
     * @deacription [KWARE] 모비젠 저장소에 저장된 데이터셋 진열등록
    **/
    @Transactional
    public void approveDataset(SaveApprovedDataset request) {
        log.info(">>> [KWARE] 모비젠 저장소에 저장된 데이터셋 진열등록");

        // 1. 모비젠 데이터셋에서 가져온 데이터 정보 진열/승인
        CetusApprovedDataset bean = new CetusApprovedDataset(request, UserUtil.getUserWorkplaceUid(), UserUtil.getUser().getUid());
        dao.insert(bean);
        Long approvedDatasetUid = bean.getUid();

        // 2. 데이터셋에 대한 카테고리 정보 저장
        Long categoryUid = datasetCategoryService.saveDatasetCategory(request.getCategory());

        // 2. 진열/승인된 데이터에 대한 UI 정보 저장 + 카테고리
        datasetUiService.saveDatasetUi(approvedDatasetUid, categoryUid, request);
    }

    /**
     * @method      findApprovedDatasetIdList
     * @author      dahyeon
     * @date        2025-10-01
     * @deacription [KWARE] 모비젠 저장소에서 kware 포탈 시스템으로 진열등록된 데이터셋 ID 목록 조회
    **/
    @Transactional(readOnly = true)
    public List<ApprovedDatasetIdList> findApprovedDatasetIdList() {
        log.info(">>> [KWARE] 모비젠 저장소에서 kware 포탈 시스템으로 진열등록된 데이터셋 ID 목록 조회");
        return dao.getApprovedDatasetIdList(UserUtil.getUserWorkplaceUid());
    }
    
    /**
     * @method      findApprovedDatasetView
     * @author      dahyeon
     * @date        2025-10-01
     * @deacription [KWARE] 모비젠 저장소에서 kware 포탈 시스템으로 진열 등록된 데이터셋에 대한 상세 정보 조회
     *
     *                  (1) 해당 데이터셋의 UI 정보도 함께 조회한다.
     *                  (2) 해당 데이터셋에 대해서 모비젠 저장소에 저장된 메타데잍터 정보도 조회한다.
     *
     * @param approvedUid : kware 포탈 시스템에 진열 등록되면서 얻게 되는 uid(pk)
    **/
    @Transactional(readOnly = true)
    public ApprovedDatasetView findApprovedDatasetView(Long approvedUid) {

        log.info(">>> [KWARE] 모비젠 저장소에서 kware 포탈 시스템으로 진열 등록된 데이터셋에 대한 상세 정보 조회");

        // 1. 진열 등록된 데이터셋 정보
        ApprovedDatasetView approvedDatasetView = dao.getApprovedDatasetView(approvedUid);

        if(approvedDatasetView != null) {
            // 2. 진열 등록된 데이터셋 UI 정보
            DatasetUiView uiView = datasetUiService.findDatasetUiView(approvedUid);
            approvedDatasetView.setUiView(uiView);

            // 3. 진열 등록된 데이터셋 UI => 모비젠에 저장된 데이터셋의 디테일 정보
            MobigenDatasetView mobigenDatasetView = mobigenDatasetService.findMobigenDatasetByDatasetId(approvedDatasetView.getDatasetId());
            approvedDatasetView.setMobigenDatasetView(mobigenDatasetView);
        }
        
        return approvedDatasetView;
    }

    /**
     * @method      deleteSeveralApprovedDataset
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [KWARE] 모비젠 저장소에서 kware 포탈 시스템으로 진열 등록된 데이터셋에 대해 논리삭제
     **/
    @Transactional
    public void deleteSeveralApprovedDataset(DeleteApprovedDatasets request) {
        log.info(">>> [KWARE] 모비젠 저장소에서 kware 포탈 시스템으로 진열 등록된 데이터셋에 대해 논리삭제");
        for (Long uid: request.getUids()) {
            CetusApprovedDataset bean = new CetusApprovedDataset(uid);
            dao.deleteApprovedDataset(bean);
        }
    }

    /**
     * @method      findHomeDatasetList
     * @author      dahyeon
     * @date        2025-10-14
     * @deacription 메인 홈 화면 데이터셋 목록 리스트 조회
    **/
    @Transactional(readOnly = true)
    public List<HomeDatasetList> findHomeDatasetList(HomeDatasetSearch search) {
        return dao.getHomeDatasetList(search);
    }
}
