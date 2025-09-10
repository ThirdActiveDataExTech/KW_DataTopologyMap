package kware.apps.manager.cetus.bbs.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.user.UserUtil;
import kware.apps.manager.cetus.bbs.domain.CetusBbs;
import kware.apps.manager.cetus.bbs.domain.CetusBbsDao;
import kware.apps.manager.cetus.bbs.dto.request.BbsChange;
import kware.apps.manager.cetus.bbs.dto.request.BbsSave;
import kware.apps.manager.cetus.bbs.dto.request.BbsSearch;
import kware.apps.manager.cetus.bbs.dto.response.BbsList;
import kware.apps.manager.cetus.bbsctt.dto.request.BbscttTpSearch;
import kware.apps.manager.cetus.enumstatus.BbsTpCd;
import kware.apps.system.menu.domain.CetusMenuInfo;
import kware.apps.system.menu.dto.request.MenuChange;
import kware.apps.system.menu.service.CetusMenuInfoService;
import kware.apps.system.program.domain.CetusProgrmInfo;
import kware.apps.manager.cetus.program.dto.request.ProgramChange;
import kware.apps.manager.cetus.program.dto.request.ProgramSave;
import kware.apps.system.program.service.CetusProgrmInfoService;
import kware.common.exception.SimpleException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CetusBbsService {

    private final CetusBbsDao dao;
    private final CetusProgrmInfoService progrmInfoService;
    private final CetusMenuInfoService menuInfoService;

    @Transactional
    public void saveBbs(BbsSave request) {
        if(!BbsTpCd.isValidCode(request.getBbsTpCd())) {
            throw new SimpleException("유효하지 않은 게시판 유형 코드입니다.");
        }
        CetusBbs bean = new CetusBbs(request);
        dao.insert(bean);

        String progrmNm = bean.getBbsNm() + " 게시판";
        String subCode = BbsTpCd.getSubCodeByCode(request.getBbsTpCd());
        String url = "/portal/bbs/" + subCode;
        progrmInfoService.saveProgram(new ProgramSave(progrmNm, url, "Y"));
    }

    @Transactional(readOnly = true)
    public Page<BbsList> findAllBbsPage(BbsSearch search, Pageable pageable) {
        search.setWorkplaceUid(UserUtil.getUserWorkplaceUid());
        Page<BbsList> bbsPage = dao.getAllBbsPage(search, pageable);
        List<BbsList> list = bbsPage.getList().stream().map(dto -> {
            dto.setBbsTpCdNm(BbsTpCd.getSubNameByCode(dto.getBbsTpCd()));
            dto.setBbsTpSubCd(BbsTpCd.getSubCodeByCode(dto.getBbsTpCd()));
            return dto;
        }).collect(Collectors.toList());
        bbsPage.setList(list);
        return bbsPage;
    }

    @Transactional(readOnly = true)
    public CetusBbs view(Long uid) {
        return dao.view(uid);
    }

    @Transactional(readOnly = true)
    public CetusBbs getBbsByTpCd(BbscttTpSearch search) {
        return dao.getBbsByTpCd(search);
    }

    @Transactional
    public void changeBbs(Long uid, BbsChange request) {
        CetusBbs view = dao.view(uid);
        dao.update(view.changeBbs(uid, request));

        String progrmNm = request.getBbsNm() + " 게시판";
        String subCode = BbsTpCd.getSubCodeByCode(view.getBbsTpCd());
        String url = "/portal/bbs/" + subCode;
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
        List<BbsList> bbsLists = dao.getAllWorkplaceBbs(UserUtil.getUserWorkplaceUid());
        return bbsLists.stream().map(dto -> {
            dto.setBbsTpCdNm(BbsTpCd.getSubNameByCode(dto.getBbsTpCd()));
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public int findBbscttCountByBbsUid(Long bbsUid) {
        return dao.getBbscttCountByBbsUid(bbsUid);
    }
}
