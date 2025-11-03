package kware.apps.system.workplace.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.system.workplace.domain.CetusWorkplace;
import kware.apps.system.workplace.domain.CetusWorkplaceDao;
import kware.apps.system.workplace.dto.request.SearchWorkplace;
import kware.apps.system.workplace.dto.request.WorkplaceSave;
import kware.apps.system.workplace.dto.response.WorkplaceList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CetusWorkplaceService {

    private final CetusWorkplaceDao dao;

    @Transactional(readOnly = true)
    public Page<WorkplaceList> findWorkplacePage(SearchWorkplace search, Pageable pageable) {
        return dao.page("getWorkplacePage", "getWorkplacePageCount", search, pageable);
    }

    @Transactional(readOnly = true)
    public CetusWorkplace findWorkplaceByUid(Long uid) {
        return dao.view(uid);
    }

    @Transactional
    public void changeWorkplace(Long uid, WorkplaceSave request) {
        CetusWorkplace bean = new CetusWorkplace(uid, request);
        dao.update(bean);
    }

    @Transactional(readOnly = true)
    public List<WorkplaceList> findWorkplaceList() {
        return dao.getWorkplaceList();
    }

    @Transactional
    public Long saveWorkplace(String workplaceNm) {
        CetusWorkplace bean = new CetusWorkplace(workplaceNm);
        dao.insert(bean);
        return bean.getUid();
    }
}
