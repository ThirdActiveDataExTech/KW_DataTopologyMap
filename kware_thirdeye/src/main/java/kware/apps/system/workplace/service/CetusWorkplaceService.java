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

@Service
@RequiredArgsConstructor
public class CetusWorkplaceService {

    private final CetusWorkplaceDao dao;

    public Page<WorkplaceList> findWorkplacePage(SearchWorkplace search, Pageable pageable) {
        return dao.page("getWorkplacePage", "getWorkplacePageCount", search, pageable);
    }

    public CetusWorkplace findWorkplaceByUid(Long uid) {
        return dao.view(uid);
    }

    public void saveWorkplace(WorkplaceSave request) {
        CetusWorkplace bean = new CetusWorkplace(request);
        dao.insert(bean);
    }

    public void changeWorkplace(Long uid, WorkplaceSave request) {
        CetusWorkplace bean = new CetusWorkplace(uid, request);
        dao.update(bean);
    }
}
