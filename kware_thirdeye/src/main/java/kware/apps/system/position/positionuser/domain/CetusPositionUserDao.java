package kware.apps.system.position.positionuser.domain;


import cetus.dao.SuperDao;
import org.springframework.stereotype.Component;

@Component
public class CetusPositionUserDao extends SuperDao<CetusPositionUser> {

    public CetusPositionUserDao() {
        super("cetusPositionUser");
    }

    public int insertPositionUser(CetusPositionUser bean) {
        return insert("insertPositionUser", bean);
    }

    public int deletePositionUser(Long userUid) {
        return delete("deletePositionUser", userUid);
    }
}
