package kware.apps.thirdeye.datasethistory.domain;

import cetus.dao.SuperDao;
import org.springframework.stereotype.Component;

@Component
public class CetusDatasetHistoryDao extends SuperDao<CetusDatasetHistory> {

    public CetusDatasetHistoryDao() {
        super("cetusDatasetHistory");
    }
}
