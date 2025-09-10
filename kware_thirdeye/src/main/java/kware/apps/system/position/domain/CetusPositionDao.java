package kware.apps.system.position.domain;


import cetus.dao.SuperDao;
import kware.apps.system.position.dto.response.PositionList;
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
}
