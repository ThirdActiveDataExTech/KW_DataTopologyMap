package kware.apps.thirdeye.mobigen.datasetfile.domain.log;

import cetus.dao.SuperDao;
import org.springframework.stereotype.Component;

@Component
public class CetusDatasetFileLogDao extends SuperDao<CetusDatasetFileLog> {

    public CetusDatasetFileLogDao() {
        super("cetusDatasetFileLog");
    }

    public void insertLog(CetusDatasetFileLog bean) {
        insert("insertLog", bean);
    }
}
