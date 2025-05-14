package kware.apps.manager.cetus.bbsctt.domain;

import cetus.dao.SuperDao;
import org.springframework.stereotype.Component;

@Component
public class CetusBbscttDao extends SuperDao<CetusBbsctt> {

    public CetusBbscttDao() {
        super("cetusBbsctt");
    }
}
