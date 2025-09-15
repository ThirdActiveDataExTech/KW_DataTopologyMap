package kware.apps.thirdeye.dataset.domain;

import cetus.dao.SuperDao;
import kware.apps.thirdeye.dataset.dto.request.DatasetViewSearch;
import kware.apps.thirdeye.dataset.dto.response.DatasetDetailView;
import org.springframework.stereotype.Component;

@Component
public class CetusDatasetDao extends SuperDao<CetusDataset> {

    public CetusDatasetDao() {
        super("cetusDataset");
    }

    public DatasetDetailView getDatasetByUid(DatasetViewSearch search) {
        return selectOne("getDatasetByUid", search);
    }
}
