package kware.apps.manager.cetus.program.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.user.UserUtil;
import kware.apps.manager.cetus.enumstatus.UserAuthorCd;
import kware.apps.manager.cetus.program.domain.CetusProgrmInfo;
import kware.apps.manager.cetus.program.domain.CetusProgrmInfoDao;
import kware.apps.manager.cetus.program.dto.request.MenuProgrmInfoSearch;
import kware.apps.manager.cetus.program.dto.request.ProgramChange;
import kware.apps.manager.cetus.program.dto.request.ProgramSave;
import kware.apps.manager.cetus.program.dto.request.ProgrmInfoSearch;
import kware.apps.manager.cetus.program.dto.response.MenuProgrmInfoList;
import kware.apps.manager.cetus.program.dto.response.ProgrmInfoList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CetusProgrmInfoService {

    private final CetusProgrmInfoDao dao;

    @Transactional(readOnly = true)
    public Page<MenuProgrmInfoList> findAllMenuProgramPage(MenuProgrmInfoSearch search, Pageable pageable) {
        search.setWorkplaceUid(UserUtil.getUserWorkplaceUid());
        return dao.page("menuProgramPageList", "menuProgramPageCount", search, pageable);
    }

    @Transactional(readOnly = true)
    public Page<ProgrmInfoList> findAllProgramPage(ProgrmInfoSearch search, Pageable pageable) {
        search.setWorkplaceUid(UserUtil.getUserWorkplaceUid());
        return dao.page("programPageList", "programPageCount", search, pageable);
    }

    @Transactional(readOnly = true)
    public CetusProgrmInfo view(Long uid) {
        return dao.view(uid);
    }

    @Transactional
    public void saveProgram(ProgramSave request) {
        dao.insert(new CetusProgrmInfo(request, UserUtil.getUserWorkplaceUid()));
    }

    @Transactional
    public void changeProgram(Long uid, ProgramChange request) {
        CetusProgrmInfo view = dao.view(uid);
        dao.update(view.changeProgram(uid, request));
    }

}
