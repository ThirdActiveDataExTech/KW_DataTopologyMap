package kware.apps.system.dept.service;

import cetus.user.UserUtil;
import kware.apps.system.dept.domain.CetusDept;
import kware.apps.system.dept.domain.CetusDeptDao;
import kware.apps.system.dept.dto.request.DeptTreeChange;
import kware.apps.system.dept.dto.request.DeptTreeSave;
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

    @Transactional(readOnly = true)
    public List<DeptTreeList> findDeptTreeListByWorkplaceUid(Long workplaceUid) {
        return dao.findDeptTreeList(workplaceUid);
    }

    @Transactional
    public void saveDeptTree(DeptTreeSave request) {
        CetusDept bean = new CetusDept(request);
        dao.insert(bean);
    }

    @Transactional
    public void saveDeptTreeRoot() {
        CetusDept bean = new CetusDept("ROOT", UserUtil.getUserWorkplaceUid());
        dao.insert(bean);
    }

    @Transactional
    public void changeDeptTree(Long uid, DeptTreeChange request) {
        CetusDept bean = new CetusDept(uid, request);
        dao.update(bean);
    }

    @Transactional
    public void deleteDeptTree(Long uid) {
        CetusDept bean = new CetusDept(uid);
        dao.updateUseAtToN(bean);
    }
}
