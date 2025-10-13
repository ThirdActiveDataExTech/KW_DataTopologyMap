package kware.apps.thirdeye.mobigen.mainui.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.user.UserUtil;
import kware.apps.thirdeye.mobigen.mainui.domain.CetusDatasetMainUi;
import kware.apps.thirdeye.mobigen.mainui.domain.CetusDatasetMainUiDao;
import kware.apps.thirdeye.mobigen.mainui.domain.DatasetMainUiType;
import kware.apps.thirdeye.mobigen.mainui.dto.request.ChangeMainUi;
import kware.apps.thirdeye.mobigen.mainui.dto.request.SaveMainUi;
import kware.apps.thirdeye.mobigen.mainui.dto.request.SearchDuplicateCode;
import kware.apps.thirdeye.mobigen.mainui.dto.request.SearchMainUi;
import kware.apps.thirdeye.mobigen.mainui.dto.response.MainUiList;
import kware.apps.thirdeye.mobigen.mainui.dto.response.MainUiView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CetusDatasetMainUiService {

    private final CetusDatasetMainUiDao dao;

    /**
     * @method      findDatasetMainUiPage
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [KWARE] 데이터셋에 대하여 화면 UI 정보 목록 페이징 조회
    **/
    @Transactional(readOnly = true)
    public Page<MainUiList> findDatasetMainUiPage(SearchMainUi search, Pageable pageable) {
        log.info(">>> [KWARE] 데이터셋에 대하여 화면 UI 정보 목록 페이징 조회");
        search.setWorkplaceUid(UserUtil.getUserWorkplaceUid());
        Page<MainUiList> page = dao.page("getDatasetMainUiPage", "getDatasetMainUiPageCount", search, pageable);
        page.getList().forEach(mainUi -> {
            DatasetMainUiType mainUiType = DatasetMainUiType.valueOf(mainUi.getTypeCd());
            mainUi.setTypeCdDescription(mainUiType.getDescription());
        });
        return page;
    }

    /**
     * @method      findCountByCode
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [KWARE] 데이터셋에 대하여 화면 UI 등록을 위한 코드 중복 체크
    **/
    @Transactional(readOnly = true)
    public Integer findCountByCode(String code) {
        log.info(">>> [KWARE] 데이터셋에 대하여 화면 UI 등록을 위한 코드 중복 체크");
        SearchDuplicateCode search = new SearchDuplicateCode(code, UserUtil.getUserWorkplaceUid());
        return dao.getCountByCode(search);
    }

    /**
     * @method      saveDatasetMainUi
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [KWARE] 데이터셋에 대하여 화면 UI 등록
    **/
    @Transactional
    public void saveDatasetMainUi(SaveMainUi request) {
        log.info(">>> [KWARE] 데이터셋에 대하여 화면 UI 등록");
        CetusDatasetMainUi bean = new CetusDatasetMainUi(request, UserUtil.getUserWorkplaceUid());
        dao.insert(bean);
    }

    /**
     * @method      findDatasetMainUiByUid
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [KWARE] 데이터셋에 대하여 화면 UI 단건 조회 (BY UID)
    **/
    @Transactional(readOnly = true)
    public MainUiView findDatasetMainUiByUid(Long uid) {
        log.info(">>> [KWARE] 데이터셋에 대하여 화면 UI 단건 조회 (BY UID)");
        return dao.getDatasetMainUiByUid(uid);
    }

    /**
     * @method      deleteDatasetMainUi
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [KWARE] 데이터셋에 대하여 화면 UI 단건 삭제 (BY UID)
    **/
    @Transactional
    public void deleteDatasetMainUi(Long uid) {
        log.info(">>> [KWARE] 데이터셋에 대하여 화면 UI 단건 삭제 (BY UID)");
        CetusDatasetMainUi bean = new CetusDatasetMainUi(uid);
        dao.updateDatasetMainUidDeleteAt(bean);
    }

    /**
     * @method      changeDatasetMainUi
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [KWARE] 데이터셋에 대하여 화면 UI 단건 수정 (BY UID)
    **/
    @Transactional
    public void changeDatasetMainUi(Long uid, ChangeMainUi request) {
        log.info(">>> [KWARE] 데이터셋에 대하여 화면 UI 단건 수정 (BY UID)");
        CetusDatasetMainUi bean = new CetusDatasetMainUi(uid, request);
        dao.update(bean);
    }

    /**
     * @method      findDatasetMainUiList
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [KWARE] 데이터셋에 대하여 화면 UI 목록 조회
    **/
    @Transactional(readOnly = true)
    public List<MainUiList> findDatasetMainUiList() {
        log.info(">>> [KWARE] 데이터셋에 대하여 화면 UI 목록 조회");
        return dao.getDatasetMainUiList(UserUtil.getUserWorkplaceUid());
    }
}
