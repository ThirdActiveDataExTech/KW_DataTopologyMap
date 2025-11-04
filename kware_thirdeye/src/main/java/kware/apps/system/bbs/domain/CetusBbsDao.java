package kware.apps.system.bbs.domain;

import cetus.dao.SuperDao;
import kware.apps.system.bbs.dto.response.BbsList;
import kware.apps.system.bbs.dto.response.BbsTpCdCount;
import kware.apps.system.bbs.dto.response.BbsView;
import kware.apps.thirdeye.bbsctt.dto.request.BbscttTpSearch;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CetusBbsDao extends SuperDao<CetusBbs> {

    public CetusBbsDao() {
        super("cetusBbs");
    }

    public CetusBbs getBbsByTpCd(BbscttTpSearch search) {
        return selectOne("getBbsByTpCd", search);
    }

    public BbsView getBbsByUid(Long uid) {
        return selectOne("getBbsByUid", uid);
    }

    public List<BbsList> getAllWorkplaceBbs(Long workplaceUid) {
        return selectList("getAllWorkplaceBbs", workplaceUid);
    }

    public int getBbscttCountByBbsUid(Long bbsUid) {
        return selectOne("getBbscttCountByBbsUid", bbsUid);
    }

    public List<BbsTpCdCount> getBbsCountByBbsTpCd(Long workplaceUid) {
        return selectList("getBbsCountByBbsTpCd", workplaceUid);
    }
}
