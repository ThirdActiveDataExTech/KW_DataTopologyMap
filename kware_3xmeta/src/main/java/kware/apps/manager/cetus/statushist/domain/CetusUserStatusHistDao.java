package kware.apps.manager.cetus.statushist.domain;

import cetus.dao.SuperDao;
import kware.apps.manager.cetus.user.domain.CetusUser;
import org.springframework.stereotype.Component;

@Component
public class CetusUserStatusHistDao extends SuperDao<CetusUserStatusHist> {

    public CetusUserStatusHistDao() {
        super("cetusUserStatusHist");
    }

    public int insertUserStatusHist(CetusUserStatusHist bean) {
        return insert("insertUserStatusHist", bean);
    }
}
