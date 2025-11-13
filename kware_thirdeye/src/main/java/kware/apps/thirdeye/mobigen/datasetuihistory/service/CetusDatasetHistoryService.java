package kware.apps.thirdeye.mobigen.datasetuihistory.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.user.UserUtil;
import cetus.util.ObjectUtil;
import kware.apps.thirdeye.mobigen.datasetui.dto.response.DatasetUiView;
import kware.apps.thirdeye.mobigen.datasetuihistory.domain.CetusDatasetHistory;
import kware.apps.thirdeye.mobigen.datasetuihistory.domain.CetusDatasetHistoryDao;
import kware.apps.thirdeye.mobigen.datasetuihistory.dto.request.SearchCetusDatasetHistory;
import kware.apps.thirdeye.mobigen.datasetuihistory.dto.response.CetusDatasetHistoryList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CetusDatasetHistoryService {

    private final CetusDatasetHistoryDao dao;

    @Transactional
    public void saveDatasetHistory(DatasetUiView view) {
        String jsonString = ObjectUtil.makeJsonString(view);
        CetusDatasetHistory bean = new CetusDatasetHistory(view.getApprovedDatasetUid(), jsonString, UserUtil.getUser().getUid());
        dao.insert(bean);
    }

    @Transactional(readOnly = true)
    public Page<CetusDatasetHistoryList> findHistoryPageByApprovedUid(SearchCetusDatasetHistory search, Pageable pageable) {
        return dao.page("getHistoryPageByApprovedUidPage", "getHistoryPageByApprovedUidCount", search, pageable);
    }
}
