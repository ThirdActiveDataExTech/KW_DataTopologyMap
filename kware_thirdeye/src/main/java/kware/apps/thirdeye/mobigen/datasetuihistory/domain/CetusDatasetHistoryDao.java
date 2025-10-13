package kware.apps.thirdeye.mobigen.datasetuihistory.domain;


import cetus.dao.SuperDao;
import org.springframework.stereotype.Component;

@Component
public class CetusDatasetHistoryDao extends SuperDao<CetusDatasetHistory> {

    public CetusDatasetHistoryDao() {
        super("cetusDatasetHistory");
    }
}
