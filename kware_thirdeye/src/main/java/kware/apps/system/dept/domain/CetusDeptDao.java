package kware.apps.system.dept.domain;


import cetus.dao.SuperDao;
import kware.apps.system.dept.dto.response.DeptTreeList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CetusDeptDao extends SuperDao<CetusDept> {

    public CetusDeptDao() {
        super("cetusDept");
    }

    public List<DeptTreeList> findDeptTreeList(Long workplaceUid) {
        return selectList("findDeptTreeList", workplaceUid);
    }

    public void updateUseAtToN(CetusDept bean) {
        update("updateUseAtToN", bean);
    }
}
