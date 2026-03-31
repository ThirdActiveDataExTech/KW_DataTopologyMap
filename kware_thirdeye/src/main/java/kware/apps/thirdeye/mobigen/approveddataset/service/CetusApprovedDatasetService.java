package kware.apps.thirdeye.mobigen.approveddataset.service;

import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.user.UserUtil;
import kware.apps.mobigen.integration.dto.request.metadata.SearchMetadataView;
import kware.apps.mobigen.integration.dto.response.metadata.MetadataView;
import kware.apps.mobigen.integration.service.DatasetService;
import kware.apps.thirdeye.bookmark.service.CetusBookMarkService2;
import kware.apps.thirdeye.mobigen.approveddataset.domain.CetusApprovedDataset;
import kware.apps.thirdeye.mobigen.approveddataset.domain.CetusApprovedDatasetDao;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.DeleteApprovedDatasets;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.HomeDatasetSearch;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.SaveApprovedDataset;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.SearchApprovedDataset;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.ApprovedDatasetItem;
import kware.apps.thirdeye.mobigen.category.service.CetusDatasetCategoryService;
import kware.apps.thirdeye.mobigen.datasetui.dto.response.DatasetUiView;
import kware.apps.thirdeye.mobigen.datasetui.service.CetusDatasetUiService;
import kware.apps.thirdeye.mobigen.mainui.dto.response.MainUiView;
import kware.apps.thirdeye.mobigen.mainui.service.CetusDatasetMainUiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CetusApprovedDatasetService {

    private final CetusApprovedDatasetDao dao;
    private final CetusApprovedDatasetService2 approvedDatasetService2;
    private final CetusDatasetUiService datasetUiService;
    private final CetusDatasetMainUiService mainUiService;

    private final CetusDatasetCategoryService datasetCategoryService;
    private final CetusBookMarkService2 bookMarkService2;

    private final DatasetService datasetService;


    private void settingApprovedDatasetInfo(ApprovedDatasetItem datasetItem) {
        String metadataId = datasetItem.getMetadataId();
        Long approvedUid = datasetItem.getApprovedUid();

        // (1) 데이터셋 > 모비젠 측을 통한 상세 정보 조회
        MetadataView metadataView = datasetService.viewMetadata(new SearchMetadataView(metadataId));
        if (metadataView != null) {
            datasetItem.setMetadataView(metadataView);
        }

        // (2) 데이터셋 진열 정보 세팅
        DatasetUiView datasetUiView = datasetUiService.findDatasetUiView(approvedUid);
        datasetItem.setDatasetUi(datasetUiView);

        // (3) 데이터싯의 UI 스키마 정보 세팅
        Long mainUiUid = datasetUiView.getMainUiUid();
        MainUiView mainUiView = mainUiService.findDatasetMainUiByUid(mainUiUid);
        datasetItem.setMainUi(mainUiView);
    }


    /**
     * @method      findDatasetPage
     * @author      dahyeon
     * @date        2025-10-01
     * @deacription [KWARE] 승인된 데이터셋 목록 조회 + 페이징
     *                  => 각 승인된 metadataId 값을 가지고 모비젠 측의 API를 통해 데이터셋 상세 정보 조회
    **/
    @Transactional(readOnly = true)
    public Page<ApprovedDatasetItem> findDatasetPage(SearchApprovedDataset search, Pageable pageable ) {
        search.setWorkplaceUid(UserUtil.getUserWorkplaceUid());
        search.setUserUid(UserUtil.getUser().getUid());
        Page<ApprovedDatasetItem> page = dao.page("getDatasetPage", "getDatasetPageCount", search, pageable);
        if(page.getList() != null && !page.getList().isEmpty()) {
            page.getList().forEach(this::settingApprovedDatasetInfo);
        }
        return page;
    }

    /**
     * @method      approveDataset
     * @author      dahyeon
     * @date        2025-10-01
     * @deacription [KWARE] 모비젠 저장소에 저장된 데이터셋 진열등록
    **/
    @Transactional
    public void approveDataset(SaveApprovedDataset request) {
        
        // 1. 모비젠 데이터셋에서 가져온 데이터 정보 진열/승인
        CetusApprovedDataset bean = new CetusApprovedDataset(request, UserUtil.getUserWorkplaceUid(), UserUtil.getUser().getUid());
        dao.insert(bean);
        Long approvedDatasetUid = bean.getUid();

        // 2. 데이터셋에 대한 카테고리 정보 저장
        Long categoryUid = (request.getCategory().getUid() == null)
                ? datasetCategoryService.saveDatasetCategory(request.getCategory())
                : request.getCategory().getUid();

        // 3. 진열/승인된 데이터에 대한 UI 정보 저장 + 카테고리
        datasetUiService.saveDatasetUi(approvedDatasetUid, categoryUid, request);
        
        // 4. 데이터셋에 대한 필터링 데이터 조회 + 저장
        // => todo 추후 수정
        /*MetadataView metadataView = datasetService.viewMetadata(
                new SearchMetadataView(request.getMetadataId()), false, false, true
        );
        approvedDatasetService2.updateDatasetSearchData(request.getMetadataId(), metadataView.getTitle(), metadataView.getTags());*/
    }
    
    /**
     * @method      findApprovedDatasetView
     * @author      dahyeon
     * @date        2025-10-01
     * @deacription [KWARE] 모비젠 저장소에서 kware 포탈 시스템으로 진열 등록된 데이터셋에 대한 상세 정보 조회
     *                  (1) 해당 데이터셋의 UI 정보도 함께 조회한다.
     *                  (2) 해당 데이터셋에 대해서 모비젠 저장소에 저장된 메타데잍터 정보도 조회한다.
     *
     * @param approvedUid : kware 포탈 시스템에 진열 등록되면서 얻게 되는 uid(pk)
    **/
    @Transactional(readOnly = true)
    public ApprovedDatasetItem findApprovedDatasetView( Long approvedUid) {
        Map<String, Object> map = Map.of(
          "approvedUid", approvedUid,
          "userUid", UserUtil.getUser().getUid()
        );
        ApprovedDatasetItem approvedDatasetView = dao.getApprovedDatasetView(map);
        this.settingApprovedDatasetInfo(approvedDatasetView);
        return approvedDatasetView;
    }

    /**
     * @method      deleteSeveralApprovedDataset
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [KWARE] 모비젠 저장소에서 kware 포탈 시스템으로 진열 등록된 데이터셋에 대해 '논리삭제'
     **/
    @Transactional
    public void deleteSeveralApprovedDataset(DeleteApprovedDatasets request) {
        for (Long uid: request.getUids()) {
            CetusApprovedDataset bean = new CetusApprovedDataset(uid);
            dao.deleteApprovedDataset(bean);
            bookMarkService2.deleteBookMarkByApprovedUid(uid);
        }
    }

    /**
     * @method      findHomeDatasetList
     * @author      dahyeon
     * @date        2025-10-14
     * @deacription 메인 홈 화면 데이터셋 목록 리스트 조회
    **/
    @Transactional(readOnly = true)
    public List<ApprovedDatasetItem> findHomeDatasetList(HomeDatasetSearch search) {
        search.setWorkplaceUid(UserUtil.getUserWorkplaceUid());
        search.setUserUid(UserUtil.getUser().getUid());
        List<ApprovedDatasetItem> homeDatasetList = dao.getHomeDatasetList(search);
        homeDatasetList.forEach(this::settingApprovedDatasetInfo);
        return homeDatasetList;
    }
}
