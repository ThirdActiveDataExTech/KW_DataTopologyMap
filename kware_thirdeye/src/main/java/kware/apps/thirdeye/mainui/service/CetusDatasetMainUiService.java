package kware.apps.thirdeye.mainui.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.user.UserUtil;
import kware.apps.thirdeye.mainui.domain.CetusDatasetMainUi;
import kware.apps.thirdeye.mainui.domain.CetusDatasetMainUiDao;
import kware.apps.thirdeye.mainui.dto.request.ChangeMainUi;
import kware.apps.thirdeye.mainui.dto.request.SaveMainUi;
import kware.apps.thirdeye.mainui.dto.request.SearchDuplicateCode;
import kware.apps.thirdeye.mainui.dto.request.SearchMainUi;
import kware.apps.thirdeye.mainui.dto.response.MainUiList;
import kware.apps.thirdeye.mainui.dto.response.MainUiView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.PushBuilder;

@Service
@RequiredArgsConstructor
public class CetusDatasetMainUiService {

    private final CetusDatasetMainUiDao dao;

    @Transactional(readOnly = true)
    public Page<MainUiList> findDatasetMainUiPage(SearchMainUi search, Pageable pageable) {
        search.setWorkplaceUid(UserUtil.getUserWorkplaceUid());
        return dao.page("getDatasetMainUiPage", "getDatasetMainUiPageCount", search, pageable);
    }

    @Transactional(readOnly = true)
    public Integer findCountByCode(String code) {
        SearchDuplicateCode search = new SearchDuplicateCode(code, UserUtil.getUserWorkplaceUid());
        return dao.getCountByCode(search);
    }

    @Transactional
    public void saveDatasetMainUi(SaveMainUi request) {
        CetusDatasetMainUi bean = new CetusDatasetMainUi(request, UserUtil.getUserWorkplaceUid());
        dao.insert(bean);
    }

    /**
     * @method      countDatasetMainUiUse
     * @author      dahyeon
     * @date        2025-10-01
     * @deacription 진열/승인된 데이터셋에 대해서 해당 main_ui 정보를 활용하는 데이터셋 개수를 가져온다.
    **/
    @Transactional(readOnly = true)
    public Integer countDatasetMainUiUse(Long uid) {
        return dao.getCountDatasetMainUiUse(uid);
    }

    @Transactional(readOnly = true)
    public MainUiView findDatasetMainUiByUid(Long uid) {
        return dao.getDatasetMainUiByUid(uid);
    }

    @Transactional
    public void deleteDatasetMainUi(Long uid) {
        CetusDatasetMainUi bean = new CetusDatasetMainUi(uid);
        dao.updateDatasetMainUidDeleteAt(bean);
    }

    @Transactional
    public void changeDatasetMainUi(Long uid, ChangeMainUi request) {
        CetusDatasetMainUi bean = new CetusDatasetMainUi(uid, request);
        dao.update(bean);
    }
}
