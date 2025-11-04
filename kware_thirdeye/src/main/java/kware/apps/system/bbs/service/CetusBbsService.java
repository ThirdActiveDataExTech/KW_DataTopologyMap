package kware.apps.system.bbs.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.user.UserUtil;
import kware.apps.superrole.code.dto.response.CodeView;
import kware.apps.superrole.code.service.CetusCodeService;
import kware.apps.system.bbs.domain.CetusBbs;
import kware.apps.system.bbs.domain.CetusBbsDao;
import kware.apps.system.bbs.dto.request.BbsChange;
import kware.apps.system.bbs.dto.request.BbsSave;
import kware.apps.system.bbs.dto.request.BbsSearch;
import kware.apps.system.bbs.dto.response.BbsList;
import kware.apps.system.bbs.dto.response.BbsTpCdCount;
import kware.apps.system.bbs.dto.response.BbsView;
import kware.apps.system.menu.domain.CetusMenuInfo;
import kware.apps.system.menu.dto.request.MenuChange;
import kware.apps.system.menu.service.CetusMenuInfoService;
import kware.apps.system.program.domain.CetusProgrmInfo;
import kware.apps.system.program.dto.request.ProgramChange;
import kware.apps.system.program.dto.request.ProgramSave;
import kware.apps.system.program.service.CetusProgrmInfoService;
import kware.apps.thirdeye.bbsctt.dto.request.BbscttTpSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CetusBbsService {

    private final CetusBbsDao dao;
    private final CetusProgrmInfoService progrmInfoService;
    private final CetusMenuInfoService menuInfoService;
    private final CetusCodeService codeService;

    @Transactional
    public void saveBbs(BbsSave request) {
        CetusBbs bean = new CetusBbs(request);
        dao.insert(bean);
        CodeView codeView = codeService.findCodeByCodeAndUpperCode(bean.getBbsTpCd(), "BBS_TP_CD");
        String progrmNm = bean.getBbsNm() + " 게시판";
        String url = "/portal/bbs/" + codeView.getItem1Val();
        progrmInfoService.saveProgram( new ProgramSave(progrmNm, url, "Y") );
    }

    @Transactional(readOnly = true)
    public Page<BbsList> findAllBbsPage(BbsSearch search, Pageable pageable) {
        search.setWorkplaceUid(UserUtil.getUserWorkplaceUid());
        return dao.page("bbsList", "bbsCount", search, pageable);
    }

    @Transactional(readOnly = true)
    public CetusBbs view(Long uid) {
        return dao.view(uid);
    }

    @Transactional(readOnly = true)
    public BbsView findBbsByUid(Long uid) {
        return dao.getBbsByUid(uid);
    }

    @Transactional(readOnly = true)
    public CetusBbs findBbsByTpCd(BbscttTpSearch search) {
        return dao.getBbsByTpCd(search);
    }

    @Transactional
    public void changeBbs(Long uid, BbsChange request) {
        CetusBbs view = dao.view(uid);
        dao.update(view.changeBbs(uid, request));

        String progrmNm = request.getBbsNm() + " 게시판";
        CodeView codeView = codeService.findCodeByCodeAndUpperCode(view.getBbsTpCd(), "BBS_TP_CD");
        String url = "/portal/bbs/" + codeView.getItem1Val();
        CetusProgrmInfo programBean = progrmInfoService.findProgramByUrl(url);
        if( programBean != null ) {
            progrmInfoService.changeProgram(programBean.getUid(), new ProgramChange(progrmNm, request.getUseAt()));
        }

        List<CetusMenuInfo> menuBeans = menuInfoService.findMenuByProgramUid(programBean.getUid());
        for(CetusMenuInfo bean : menuBeans) {
            String menuNm = request.getBbsNm();
            menuInfoService.changeMenu(bean.getMenuNo(), new MenuChange(menuNm, request.getUseAt()));
        }
    }

    @Transactional
    public void deleteBbs(Long uid) {
        dao.delete(uid);
    }

    @Transactional(readOnly = true)
    public List<BbsList> findAllWorkplaceBbs() {
        return dao.getAllWorkplaceBbs(UserUtil.getUserWorkplaceUid());
    }

    @Transactional(readOnly = true)
    public int findBbscttCountByBbsUid(Long bbsUid) {
        return dao.getBbscttCountByBbsUid(bbsUid);
    }

    @Transactional(readOnly = true)
    public List<BbsTpCdCount> findBbsCountByBbsTpCd(Long workplaceUid) {
        return dao.getBbsCountByBbsTpCd(workplaceUid);
    }
}
