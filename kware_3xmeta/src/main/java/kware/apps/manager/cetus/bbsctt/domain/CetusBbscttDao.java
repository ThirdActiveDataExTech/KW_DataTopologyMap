package kware.apps.manager.cetus.bbsctt.domain;

import cetus.dao.SuperDao;
import kware.apps.manager.cetus.bbsctt.dto.response.BbscttView;
import org.springframework.stereotype.Component;

@Component
public class CetusBbscttDao extends SuperDao<CetusBbsctt> {

    public CetusBbscttDao() {
        super("cetusBbsctt");
    }

    public int increaseViewCount(Long bbscttUid) {
        return update("increaseViewCount", bbscttUid);
    }

    public BbscttView getViewByBbscttUid(Long bbscttUid) {
        return selectOne("getViewByBbscttUid", bbscttUid);
    }
}
