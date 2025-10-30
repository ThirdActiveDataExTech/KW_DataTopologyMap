package kware.apps.thirdeye.mobigen.datasetuihistory.service;


import cetus.user.UserUtil;
import cetus.util.ObjectUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kware.apps.thirdeye.mobigen.datasetui.dto.response.DatasetUiView;
import kware.apps.thirdeye.mobigen.datasetuihistory.domain.CetusDatasetHistory;
import kware.apps.thirdeye.mobigen.datasetuihistory.domain.CetusDatasetHistoryDao;
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
}
