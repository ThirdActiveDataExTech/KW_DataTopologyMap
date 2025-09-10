package kware.apps.system.loginhist.domain;


import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.dao.SuperDao;
import kware.apps.system.loginhist.dto.request.UserLoginHistExcelSearch;
import kware.apps.system.loginhist.dto.response.UserLoginHistExcelList;
import org.springframework.stereotype.Component;

@Component
public class CetusUserLoginHistDao extends SuperDao<CetusUserLoginHist> {

    public CetusUserLoginHistDao() {
        super("cetusUserLoginHist");
    }

    public Page<UserLoginHistExcelList> userLoginHistExcelPage(UserLoginHistExcelSearch search, Pageable pageable) {
        return page("userLoginHistExcelList", "userLoginHistExcelCount", search, pageable);
    }
}
