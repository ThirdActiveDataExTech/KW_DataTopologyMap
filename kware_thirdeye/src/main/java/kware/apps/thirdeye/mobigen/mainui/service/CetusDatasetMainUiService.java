package kware.apps.thirdeye.mobigen.mainui.service;


import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
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
        return dao.getDatasetMainUiByUid(uid);
    }

    /** 목록 한 페이지가 쓰는 메인 UI 들을 한 번에 조회해 UID 별로 돌려준다 */
    @Transactional(readOnly = true)
    public Map<Long, MainUiView> findDatasetMainUiMap(List<Long> uids) {
        if (uids == null || uids.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<Long, MainUiView> result = new HashMap<>();
        for (MainUiView view : dao.getDatasetMainUiListByUids(uids)) {
            result.putIfAbsent(view.getUid(), view);
        }
        return result;
    }

    /**
     * @method      deleteDatasetMainUi
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [KWARE] 데이터셋에 대하여 화면 UI 단건 삭제 (BY UID)
    **/
    @Transactional
    public void deleteDatasetMainUi(Long uid) {
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
        return dao.getDatasetMainUiList(UserUtil.getUserWorkplaceUid());
    }
}
