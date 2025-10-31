package kware.apps.system.workplace.domain;

import cetus.dao.SuperDao;
import kware.apps.system.workplace.dto.response.WorkplaceList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CetusWorkplaceDao extends SuperDao<CetusWorkplace> {

    public CetusWorkplaceDao() {
        super("cetusWorkplace");
    }

    public List<WorkplaceList> getWorkplaceList() {
        return selectList("getWorkplaceList");
    }
}
