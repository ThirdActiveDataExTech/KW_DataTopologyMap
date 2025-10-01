package kware.apps.thirdeye.datasetuihistory.service;


import cetus.user.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kware.apps.thirdeye.datasetui.dto.response.DatasetUiView;
import kware.apps.thirdeye.datasetuihistory.domain.CetusDatasetHistory;
import kware.apps.thirdeye.datasetuihistory.domain.CetusDatasetHistoryDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CetusDatasetHistoryService {

    private final CetusDatasetHistoryDao dao;

    @Transactional
    public void saveDatasetHistory(DatasetUiView view) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(view);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        CetusDatasetHistory bean = new CetusDatasetHistory(view.getApprovedDatasetUid(), jsonString, UserUtil.getUser().getUid());
        dao.insert(bean);
    }
}
