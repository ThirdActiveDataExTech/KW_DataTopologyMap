package kware.apps.system.dept.service;

import cetus.user.UserUtil;
import kware.apps.system.dept.domain.CetusDeptDao;
import kware.apps.system.dept.dto.response.DeptTreeList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CetusDeptService {

    private final CetusDeptDao dao;

    @Transactional(readOnly = true)
    public List<DeptTreeList> findDeptTreeList() {
        return dao.findDeptTreeList(UserUtil.getUser().getWorkplaceUid());
    }
}
