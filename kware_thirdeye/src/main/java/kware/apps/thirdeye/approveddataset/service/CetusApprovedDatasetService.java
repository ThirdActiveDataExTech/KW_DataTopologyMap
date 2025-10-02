package kware.apps.thirdeye.approveddataset.service;

import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.user.UserUtil;
import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetView;
import kware.apps.mobigen.cetus.dataset.service.CetusMobigenDatasetService;
import kware.apps.thirdeye.approveddataset.domain.CetusApprovedDataset;
import kware.apps.thirdeye.approveddataset.domain.CetusApprovedDatasetDao;
import kware.apps.thirdeye.approveddataset.dto.request.ApprovedDatasetSearch;
import kware.apps.thirdeye.approveddataset.dto.request.SaveApprovedDataset;
import kware.apps.thirdeye.approveddataset.dto.response.ApprovedDatasetIdList;
import kware.apps.thirdeye.approveddataset.dto.response.ApprovedDatasetView;
import kware.apps.thirdeye.approveddataset.dto.response.DatasetList;
import kware.apps.thirdeye.datasetui.dto.response.DatasetUiView;
import kware.apps.thirdeye.datasetui.service.CetusDatasetUiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CetusApprovedDatasetService {

    private final CetusApprovedDatasetDao dao;
    private final CetusDatasetUiService datasetUiService;
    private final CetusMobigenDatasetService mobigenDatasetService;

    /**
     * @method      findDatasetPage
     * @author      dahyeon
     * @date        2025-10-01
     * @deacription 승인된 데이터셋 목록 조회 + 페이징
    **/
    @Transactional(readOnly = true)
    public Page<DatasetList> findDatasetPage(ApprovedDatasetSearch search, Pageable pageable ) {
        search.setWorkplaceUid(UserUtil.getUserWorkplaceUid());
        Page<DatasetList> page = dao.page("getDatasetPage", "getDatasetPageCount", search, pageable);
        if(page.getList() != null && !page.getList().isEmpty()) {
            page.getList().forEach(dataset -> {
                Long datasetId = dataset.getDatasetId();
                MobigenDatasetView datasetView = mobigenDatasetService.findMobigenDatasetByDatasetId(datasetId);
                if (datasetView != null) {
                    dataset.setDatasetInfo(datasetView);
                }
            });
        }
        return page;
    }

    /**
     * @method      findDatasetList
     * @author      dahyeon
     * @date        2025-10-01
     * @deacription 승인된 데이터셋 목록 조회 (리스트)
     **/
    @Transactional(readOnly = true)
    public List<DatasetList> findDatasetList( ApprovedDatasetSearch search ) {
        search.setWorkplaceUid(UserUtil.getUserWorkplaceUid());
        List<DatasetList> list = dao.getDatasetList(search);
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
     * @deacription 모비젠 저장소에 저장된 데이터셋 진열등록
    **/
    @Transactional
    public void approveDataset(SaveApprovedDataset request) {

        // 1. 모비젠 데이터셋에서 가져온 데이터 정보 진열/승인
        CetusApprovedDataset bean = new CetusApprovedDataset(request, UserUtil.getUserWorkplaceUid(), UserUtil.getUser().getUid());
        dao.insert(bean);
        Long approvedDatasetUid = bean.getUid();

        // 2. 진열/승인된 데이터에 대한 UI 정보 저장
        datasetUiService.saveDatasetUi(approvedDatasetUid, request);
    }

    /**
     * @method      findApprovedDatasetIdList
     * @author      dahyeon
     * @date        2025-10-01
     * @deacription 모비젠 저장소에서 kware 포탈 시스템으로 진열등록된 데이터셋 ID 목록
    **/
    @Transactional(readOnly = true)
    public List<ApprovedDatasetIdList> findApprovedDatasetIdList() {
        return dao.getApprovedDatasetIdList(UserUtil.getUserWorkplaceUid());
    }
    
    /**
     * @method      findApprovedDatasetView
     * @author      dahyeon
     * @date        2025-10-01
     * @deacription 모비젠 저장소에서 kware 포탈 시스템으로 진열 등록된 데이터셋에 대한 상세 정보 조회
     *              => 해당 데이터셋의 UI 정보도 함께 조회한다.
     *              => 해당 데이터셋에 대해서 모비젠 저장소에 저장된 메타데잍터 정보도 조회한다.
     *
     * @param approvedUid : kware 포탈 시스템에 진열 등록되면서 얻게 되는 uid(pk)
    **/
    @Transactional(readOnly = true)
    public ApprovedDatasetView findApprovedDatasetView(Long approvedUid) {
        ApprovedDatasetView approvedDatasetView = dao.getApprovedDatasetView(approvedUid);

        DatasetUiView uiView = datasetUiService.findDatasetUiView(approvedUid);
        approvedDatasetView.setUiView(uiView);

        MobigenDatasetView mobigenDatasetView = mobigenDatasetService.findMobigenDatasetByDatasetId(approvedDatasetView.getDatasetId());
        approvedDatasetView.setMobigenDatasetView(mobigenDatasetView);
        
        return approvedDatasetView;
    }
}
