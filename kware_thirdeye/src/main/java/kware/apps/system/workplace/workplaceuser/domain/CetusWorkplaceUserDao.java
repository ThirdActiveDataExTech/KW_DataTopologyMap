package kware.apps.system.workplace.workplaceuser.domain;

import cetus.dao.SuperDao;
import org.springframework.stereotype.Component;

@Component
public class CetusWorkplaceUserDao extends SuperDao<CetusWorkplaceUser> {

    public CetusWorkplaceUserDao() {
        super("cetusWorkplaceUser");
    }

    public int insertWorkplaceUser(CetusWorkplaceUser bean) {
        return insert("insertWorkplaceUser", bean);
    }
}
