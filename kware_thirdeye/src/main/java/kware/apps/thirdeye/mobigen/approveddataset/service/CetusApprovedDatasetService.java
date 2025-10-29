package kware.apps.thirdeye.mobigen.approveddataset.service;

import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.user.UserUtil;
import kware.apps.mobigen.cetus.tag.dto.response.TagList;
import kware.apps.mobigen.integration.dto.request.metadata.SearchMetadataView;
import kware.apps.mobigen.integration.dto.response.metadata.MetadataView;
import kware.apps.mobigen.integration.service.DatasetService;
import kware.apps.thirdeye.mobigen.approveddataset.domain.ApprovedDatasetTargetTpCd;
import kware.apps.thirdeye.mobigen.approveddataset.domain.CetusApprovedDataset;
import kware.apps.thirdeye.mobigen.approveddataset.domain.CetusApprovedDatasetDao;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.*;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.ApprovedDatasetList;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.ApprovedDatasetView;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class CetusApprovedDatasetService {

    private final CetusApprovedDatasetDao dao;
    private final CetusApprovedDatasetService2 approvedDatasetService2;
    private final CetusDatasetUiService datasetUiService;

    private final CetusDatasetCategoryService datasetCategoryService;

    private final DatasetService datasetService;

    /**
     * @method      findDatasetPage
     * @author      dahyeon
     * @date        2025-10-01
     * @deacription [KWARE] 승인된 데이터셋 목록 조회 + 페이징
     *                  => 각 승인된 metadataId 값을 가지고 모비젠 측의 API를 통해 데이터셋 상세 정보 조회
    **/
    @Transactional(readOnly = true)
    public Page<ApprovedDatasetList> findDatasetPage( ApprovedDatasetSearch search, Pageable pageable ) {
        search.setWorkplaceUid(UserUtil.getUserWorkplaceUid());
        search.setUserUid(UserUtil.getUser().getUid());
        Page<ApprovedDatasetList> page = dao.page("getDatasetPage", "getDatasetPageCount", search, pageable);
        if(page.getList() != null && !page.getList().isEmpty()) {
            page.getList().forEach(dataset -> {
                // (1) 데이터셋 > 모비젠 측을 통한 상세 정보 조회
                Long metadataId = dataset.getMetadataId();
                MetadataView metadataView = datasetService.viewMetadata(new SearchMetadataView(Long.toString(metadataId)));
                if (metadataView != null) {
                    dataset.setMetadataView(metadataView);
                }
                // (2) 데이터셋의 화면 UI 값에 대해서 > 해당 UI 설명 정보
                if(dataset.getMainUiTypeCd() != null) {
                    DatasetMainUiType mainUiType = DatasetMainUiType.valueOf(dataset.getMainUiTypeCd());
                    dataset.setMainUiTypeCdDesc(mainUiType.getDescription());
                }
                // (3) 원본 데이터셋 저장 위치 정보
                if(dataset.getTargetTpCd() != null) {
                    ApprovedDatasetTargetTpCd targetTpCd = ApprovedDatasetTargetTpCd.valueOf(dataset.getTargetTpCd());
                    dataset.setTargetTpCdNm(targetTpCd.getDescription());
                }
            });
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
        MetadataView metadataView = datasetService.viewMetadata(new SearchMetadataView(Long.toString(request.getMetadataId())));
        String title = metadataView.getTitle();
        List<TagList> tags = metadataView.getTags();
        approvedDatasetService2.updateDatasetSearchData(request.getMetadataId(), title, tags);
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
        // 1. 진열 등록된 데이터셋 정보
        ApprovedDatasetView approvedDatasetView = dao.getApprovedDatasetView(new SearchApprovedDatasetView(approvedUid, UserUtil.getUser().getUid()));
        if(approvedDatasetView != null) {

            // 2. 진열 등록된 데이터셋 UI 정보
            DatasetUiView uiView = datasetUiService.findDatasetUiView(approvedUid);
            approvedDatasetView.setUiView(uiView);

            // 3. 진열 등록된 데이터셋 UI => 모비젠에 저장된 데이터셋의 디테일 정보
            MetadataView metadataView = datasetService.viewMetadata(new SearchMetadataView(Long.toString(approvedDatasetView.getMetadataId())));
            approvedDatasetView.setMetadataView(metadataView);

            // 4. 원본 데이터셋 저장 위치 정보
            if(approvedDatasetView.getTargetTpCd() != null) {
                ApprovedDatasetTargetTpCd targetTpCd = ApprovedDatasetTargetTpCd.valueOf(approvedDatasetView.getTargetTpCd());
                approvedDatasetView.setTargetTpCdNm(targetTpCd.getDescription());
            }
        }
        
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
        search.setWorkplaceUid(UserUtil.getUserWorkplaceUid());
        search.setUserUid(UserUtil.getUser().getUid());
        List<HomeDatasetList> homeDatasetList = dao.getHomeDatasetList(search);
        homeDatasetList.forEach(home -> {
            // 1. 진열관리 중인 데이터셋에 대한 상세 정보 > 모비젠 측을 통한 조회
            Long metadataId = home.getMetadataId();
            MetadataView metadataView = datasetService.viewMetadata(new SearchMetadataView(Long.toString(metadataId)));
            home.setMetadataView(metadataView);
            
            // 2. 진열관리 중인 데이터셋에 대한 > 화면 UI 정보 조회
            Long approvedUid = home.getApprovedUid();
            DatasetUiView uiView = datasetUiService.findDatasetUiView(approvedUid);
            home.setUiView(uiView);

            // 3. 원본 데이터셋 저장 위치 정보
            if(home.getTargetTpCd() != null) {
                ApprovedDatasetTargetTpCd targetTpCd = ApprovedDatasetTargetTpCd.valueOf(home.getTargetTpCd());
                home.setTargetTpCdNm(targetTpCd.getDescription());
            }
        });
        return homeDatasetList;
    }
}
