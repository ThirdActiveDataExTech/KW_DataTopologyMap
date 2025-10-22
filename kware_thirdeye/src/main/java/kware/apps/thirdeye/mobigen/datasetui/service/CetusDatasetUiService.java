package kware.apps.thirdeye.mobigen.datasetui.service;


import cetus.user.UserUtil;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.SaveApprovedDataset;
import kware.apps.thirdeye.mobigen.category.service.CetusDatasetCategoryService;
import kware.apps.thirdeye.mobigen.datasetui.domain.CetusDatasetUi;
import kware.apps.thirdeye.mobigen.datasetui.domain.CetusDatasetUiDao;
import kware.apps.thirdeye.mobigen.datasetui.dto.request.ChangeDatasetUi;
import kware.apps.thirdeye.mobigen.datasetui.dto.request.ChangeShowUiDatasets;
import kware.apps.thirdeye.mobigen.datasetui.dto.response.DatasetUiGroup;
import kware.apps.thirdeye.mobigen.datasetui.dto.response.DatasetUiView;
import kware.apps.thirdeye.mobigen.datasetuihistory.service.CetusDatasetHistoryService;
import kware.apps.thirdeye.mobigen.mainui.domain.DatasetMainUiType;
import kware.common.file.service.CommonFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CetusDatasetUiService {

    private final CetusDatasetUiDao dao;
    private final CommonFileService commonFileService;
    private final CetusDatasetHistoryService historyService;

    private final CetusDatasetCategoryService datasetCategoryService;

    /**
     * @method      countDatasetMainUiUse
     * @author      dahyeon
     * @date        2025-10-01
     * @deacription [KWARE] 진열/승인된 데이터셋에 대해서 해당 main_ui 정보를 활용하는 데이터셋 개수 조회
     *              => main_ui 정보를 활용하는 데이터셋이 있다면 해당 main_ui 정보는 삭제 불가능
     **/
    @Transactional(readOnly = true)
    public Integer countDatasetMainUiUse(Long uid) {
        return dao.getCountDatasetMainUiUse(uid);
    }

    /**
     * @method      saveDatasetUi
     * @author      dahyeon
     * @date        2025-10-01
     * @deacription [KWARE] 모비젠 데이터 저장소의 데이터셋을 진열등록하는 시점에 해당 데이터셋에 대한 UI 정보 저장
    **/
    @Transactional
    public void saveDatasetUi(Long approvedDatasetUid, Long categoryUid, SaveApprovedDataset request) {

        // 1. 썸네일 있다면 파일 정보 저장
        Long thumbUid = null;
        thumbUid = commonFileService.processFileBean(request, UserUtil.getUser(), thumbUid);

        // 2. 데이터셋의 UI 정보 저장
        CetusDatasetUi bean = new CetusDatasetUi(approvedDatasetUid, categoryUid, thumbUid, request);
        dao.insert(bean);
    }

    /**
     * @method      findDatasetUiView
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [KWARE] kware 포탈 시스템에 진열 등록된 데이터셋 UI 정보 조회
     **/
    @Transactional(readOnly = true)
    public DatasetUiView findDatasetUiView(Long approvedDatasetUid) {
        return dao.getDatasetUiView(approvedDatasetUid);
    }

    /**
     * @method      changeApprovedDatasetUi
     * @author      dahyeon
     * @date        2025-10-01
     * @deacription [KWARE] kware 포탈 시스템에 진열 등록된 데이터셋 UI 정보 수정
     *
     *                  (1) 기존 UI 정보 이력에 담아서 저장
     *                  (2) 썸네일 파일 정보 수정
     *                  (3) 카테고리 정보 변경시, 해당 정보 -> 이미 있는 카테고리면 넘기고, 없는 카테고리면 저장
     *                  (3) 최종 데이터셋 UI 정보 수정
     **/
    @Transactional
    public void changeApprovedDatasetUi(Long approvedDatasetUid, ChangeDatasetUi request) {

        // 1. 기존 정보 가져와서 history 담기
        DatasetUiView uiView = dao.getDatasetUiView(approvedDatasetUid);
        historyService.saveDatasetHistory(uiView);
        Long datasetUiUid = uiView.getDatasetUiUid();

        // 2. 썸네일 있다면 파일 저장/삭제
        Long thumbUid = request.getThumbUid();
        thumbUid = commonFileService.processFileBean(request, UserUtil.getUser(), thumbUid);

        // 3. 카테고리 정보
        Long categoryUid = (request.getCategory().getUid() == null)
                ? datasetCategoryService.saveDatasetCategory(request.getCategory())
                : request.getCategory().getUid();

        // 4. 최종 수정
        CetusDatasetUi bean = new CetusDatasetUi(datasetUiUid, request, categoryUid, thumbUid);
        dao.update(bean);
    }

    /**
     * @method      findDatasetUiByGroup
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription 데이터셋 UI로 사용중인 MAIN_UI 정보를 그룹핑해서 가져온다.
    **/
    @Transactional(readOnly = true)
    public List<DatasetUiGroup> findDatasetUiByGroup() {
        List<DatasetUiGroup> datasetUiByGroup = dao.getDatasetUiByGroup(UserUtil.getUserWorkplaceUid());
        datasetUiByGroup.forEach(dto -> {
            DatasetMainUiType mainUiType = DatasetMainUiType.valueOf(dto.getTypeCd());
            dto.setUseInfo(mainUiType);
        });
        return datasetUiByGroup;
    }

    @Transactional
    public void changeShowApprovedDataset(ChangeShowUiDatasets request, String showAt) {
        for (Long approvedDatasetUid: request.getUids()) {
            // 1. 수정전, 이력 저장
            DatasetUiView uiView = dao.getDatasetUiView(approvedDatasetUid);
            historyService.saveDatasetHistory(uiView);
            Long datasetUiUid = uiView.getDatasetUiUid();

            // 2. 공개여부 수정
            CetusDatasetUi bean = new CetusDatasetUi(datasetUiUid, showAt);
            dao.updateDatasetUiShowAt(bean);
        }
    }
}
