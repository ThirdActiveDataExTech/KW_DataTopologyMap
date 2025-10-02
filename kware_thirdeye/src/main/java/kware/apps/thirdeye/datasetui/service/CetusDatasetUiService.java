package kware.apps.thirdeye.datasetui.service;


import cetus.user.UserUtil;
import kware.apps.thirdeye.datasetui.dto.request.ChangeDatasetUi;
import kware.apps.thirdeye.approveddataset.dto.request.SaveApprovedDataset;
import kware.apps.thirdeye.datasetui.domain.CetusDatasetUi;
import kware.apps.thirdeye.datasetui.domain.CetusDatasetUiDao;
import kware.apps.thirdeye.datasetui.dto.response.DatasetUiView;
import kware.apps.thirdeye.datasetuihistory.service.CetusDatasetHistoryService;
import kware.common.file.service.CommonFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CetusDatasetUiService {

    private final CetusDatasetUiDao dao;
    private final CommonFileService commonFileService;
    private final CetusDatasetHistoryService historyService;

    /**
     * @method      countDatasetMainUiUse
     * @author      dahyeon
     * @date        2025-10-01
     * @deacription 진열/승인된 데이터셋에 대해서 해당 main_ui 정보를 활용하는 데이터셋 개수를 가져온다.
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
     * @deacription 모비젠 데이터 저장소의 데이터셋을 진열등록하는 시점에 해당 데이터셋에 대한 UI 정보를 저장한다.
    **/
    @Transactional
    public void saveDatasetUi(Long approvedDatasetUid, SaveApprovedDataset request) {

        // 1. 썸네일 있다면 파일 정보 저장
        Long thumbUid = null;
        thumbUid = commonFileService.processFileBean(request, UserUtil.getUser(), thumbUid);

        // 2. 데이터셋의 UI 정보 저장
        CetusDatasetUi bean = new CetusDatasetUi(approvedDatasetUid, thumbUid, request);
        dao.insert(bean);
    }

    @Transactional(readOnly = true)
    public DatasetUiView findDatasetUiView(Long approvedDatasetUid) {
        return dao.getDatasetUiView(approvedDatasetUid);
    }

    /**
     * @method      changeApprovedDatasetUi
     * @author      dahyeon
     * @date        2025-10-01
     * @deacription kware 포탈 시스템에 진열 등록된 데이터셋 UI 정보 수정
     **/
    @Transactional
    public void changeApprovedDatasetUi(Long uid, ChangeDatasetUi request) {

        // 1. 기존 정보 가져와서 history 담기
        DatasetUiView uiView = dao.getDatasetUiView(uid);
        historyService.saveDatasetHistory(uiView);

        // 2. 썸네일 있다면 파일 저장/삭제
        Long thumbUid = request.getThumbUid();
        thumbUid = commonFileService.processFileBean(request, UserUtil.getUser(), thumbUid);

        // 3. 최종 수정
        CetusDatasetUi bean = new CetusDatasetUi(uid, request, thumbUid);
        dao.update(bean);
    }
}
