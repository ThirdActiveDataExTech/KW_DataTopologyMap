package kware.apps.system.statushist.domain;

import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.dao.SuperDao;
import kware.apps.system.statushist.dto.request.UserStatusHistExcelSearch;
import kware.apps.system.statushist.dto.response.UserStatusHistExcelList;
import org.springframework.stereotype.Component;

@Component
public class CetusUserStatusHistDao extends SuperDao<CetusUserStatusHist> {

    public CetusUserStatusHistDao() {
        super("cetusUserStatusHist");
    }

    public int insertUserStatusHist(CetusUserStatusHist bean) {
        return insert("insertUserStatusHist", bean);
    }

    public Page<UserStatusHistExcelList> userStatusHistExcelPage(UserStatusHistExcelSearch search, Pageable pageable) {
        return page("userStatusHistExcelList", "userStatusHistExcelCount", search, pageable);
    }
}
