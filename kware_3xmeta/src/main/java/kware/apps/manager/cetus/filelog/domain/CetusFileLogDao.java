package kware.apps.manager.cetus.filelog.domain;

import cetus.dao.SuperDao;
import org.springframework.stereotype.Component;

@Component
public class CetusFileLogDao extends SuperDao<CetusFileLog> {

    public CetusFileLogDao() {
        super("cetusFileLog");
    }
}
