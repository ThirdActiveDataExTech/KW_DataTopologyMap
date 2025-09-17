package kware.apps.system.position.domain;


import cetus.dao.SuperDao;
import kware.apps.system.position.dto.response.PositionList;
import kware.apps.system.position.dto.response.PositionListWithUser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CetusPositionDao extends SuperDao<CetusPosition> {

    public CetusPositionDao() {
        super("cetusPosition");
    }

    public List<PositionList> getPositionList(Long workplaceUid) {
        return selectList("getPositionList", workplaceUid);
    }

    public List<PositionListWithUser> getPositionListWithUser(Long workplaceUid) {
        return selectList("getPositionListWithUser", workplaceUid);
    }

    public void updateUseAtToN(CetusPosition bean) {
        update("updateUseAtToN", bean);
    }
}
