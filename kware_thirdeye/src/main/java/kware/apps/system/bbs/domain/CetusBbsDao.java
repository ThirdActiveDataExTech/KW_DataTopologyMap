package kware.apps.system.bbs.domain;

import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.dao.SuperDao;
import kware.apps.system.bbs.dto.request.BbsSearch;
import kware.apps.system.bbs.dto.response.BbsList;
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

    public Page<BbsList> getAllBbsPage(BbsSearch search, Pageable pageable) {
        return page("bbsList", "bbsCount", search, pageable);
    }

    public List<BbsList> getAllWorkplaceBbs(Long workplaceUid) {
        return selectList("getAllWorkplaceBbs", workplaceUid);
    }

    public int getBbscttCountByBbsUid(Long bbsUid) {
        return selectOne("getBbscttCountByBbsUid", bbsUid);
    }
}
