package kware.apps.thirdeye.mobigen.datasetui.domain;


import cetus.dao.SuperDao;
import kware.apps.thirdeye.mobigen.datasetui.dto.response.DatasetUiView;
import org.springframework.stereotype.Component;

@Component
public class CetusDatasetUiDao extends SuperDao<CetusDatasetUi> {

    public CetusDatasetUiDao() {
        super("cetusDatasetUi");
    }

    public Integer getCountDatasetMainUiUse(Long uid) {
        return selectOne("getCountDatasetMainUiUse", uid);
    }

    public DatasetUiView getDatasetUiView(Long approvedDatasetUid) {
        return selectOne("getDatasetUiView", approvedDatasetUid);
    }
}
