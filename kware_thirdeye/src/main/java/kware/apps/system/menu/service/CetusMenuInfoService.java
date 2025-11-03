package kware.apps.system.menu.service;

import cetus.user.UserUtil;
import kware.apps.superrole.dto.request.createworkplace.CreateMenu;
import kware.apps.system.menu.domain.CetusMenuInfo;
import kware.apps.system.menu.domain.CetusMenuInfoDao;
import kware.apps.system.menu.dto.request.*;
import kware.apps.system.menu.dto.response.MenuTreeList;
import kware.apps.system.menu.dto.response.SessionMenuList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CetusMenuInfoService {

    private final CetusMenuInfoDao dao;

    /*
    * 로그인 시점 > 세션에 담기 위한 메뉴 조회
    * */
    @Transactional(readOnly = true)
    public List<SessionMenuList> getSessionMenuList(SessionMenuListSearch search) {
        return dao.findSessionMenuList(search);
    }

    @Transactional(readOnly = true)
    public List<MenuTreeList> getMenuTreeList(MenuTreeSearch search) {
        search.setWorkplaceUid(UserUtil.getUserWorkplaceUid());
        return dao.findMenuTreeList(search);
    }

    @Transactional(readOnly = true)
    public SessionMenuList getRootMenuInfo(Long rootMenuNo) {
        return dao.getRootMenuInfo(rootMenuNo);
    }

    @Transactional(readOnly = true)
    public Long getRootMenuNo(String authorCd, String rootMenuCd, Long workplaceUid) {
        MenuRootSearch search = new MenuRootSearch(authorCd, rootMenuCd, workplaceUid);
        return dao.getRootMenuNo(search);
    }

    @Transactional
    public void saveMenu(MenuSave request) {
        dao.insert(new CetusMenuInfo(request, UserUtil.getUserWorkplaceUid()));
    }

    @Transactional
    public Long saveWorkplaceMenu( CreateMenu request, Long workplaceUid, Long programUid, Long upperMenuNo, String authorCd ) {
        CetusMenuInfo bean = new CetusMenuInfo(request, workplaceUid, programUid, upperMenuNo, authorCd);
        dao.insert(bean);
        return bean.getMenuNo();
    }

    @Transactional
    public void changeMenu(Long menuNo, MenuChange request) {
        CetusMenuInfo view = dao.view(menuNo);
        dao.update(view.changeMenu(menuNo, request));
    }

    @Transactional(readOnly = true)
    public List<CetusMenuInfo> findMenuByProgramUid(Long programUid) {
        return dao.getMenuByProgramUid(programUid);
    }

    @Transactional
    public void deleteMenu(Long menuNo) {
        dao.delete(menuNo);
    }

    @Transactional(readOnly = true)
    public CetusMenuInfo view(Long menuNo) {
        return dao.view(menuNo);
    }
}
