package kware.apps.manager.cetus.program.domain;

import cetus.dao.SuperDao;
import org.springframework.stereotype.Component;

@Component
public class CetusProgrmInfoDao extends SuperDao<CetusProgrmInfo> {

    public CetusProgrmInfoDao() {
        super("cetusProgrmInfo");
    }
}
