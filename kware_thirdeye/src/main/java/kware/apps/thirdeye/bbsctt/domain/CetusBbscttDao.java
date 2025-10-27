package kware.apps.thirdeye.bbsctt.domain;

import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.dao.SuperDao;
import kware.apps.thirdeye.bbsctt.dto.request.BbscttExcelSearch;
import kware.apps.thirdeye.bbsctt.dto.response.BbscttExcelList;
import kware.apps.thirdeye.bbsctt.dto.response.BbscttView;
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

    public int updateBbscttOpenAt(CetusBbsctt bean) {
        return update("updateBbscttOpenAt", bean);
    }

    public Page<BbscttExcelList> bbscttExcelPage(BbscttExcelSearch search, Pageable pageable) {
        return page("bbscttExcelPageList", "bbscttExcelPageListCount", search, pageable);
    }

    public void updateBbscttLikeCount(Long uid) {
        update("updateBbscttLikeCount", uid);
    }

    public Integer getBbscttLikeCount(Long uid) {
        return selectOne("getBbscttLikeCount", uid);
    }
}
