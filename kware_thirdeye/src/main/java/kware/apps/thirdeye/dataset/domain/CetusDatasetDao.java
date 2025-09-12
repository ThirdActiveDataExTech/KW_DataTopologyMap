package kware.apps.thirdeye.dataset.domain;

import cetus.dao.SuperDao;
import org.springframework.stereotype.Component;

@Component
public class CetusDatasetDao extends SuperDao<CetusDataset> {

    public CetusDatasetDao() {
        super("cetusDataset");
    }
}
