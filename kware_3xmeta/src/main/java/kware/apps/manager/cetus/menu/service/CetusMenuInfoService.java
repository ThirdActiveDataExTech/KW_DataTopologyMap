package kware.apps.manager.cetus.menu.service;

import java.util.List;

import cetus.user.UserUtil;
import kware.apps.manager.cetus.enumstatus.MenuRootCd;
import kware.apps.manager.cetus.enumstatus.UserAuthorCd;
import kware.apps.manager.cetus.menu.domain.CetusMenu;
import kware.apps.manager.cetus.menu.domain.CetusMenuInfo;
import kware.apps.manager.cetus.menu.domain.CetusMenuInfoDao;
import kware.apps.manager.cetus.menu.dto.request.MenuChange;
import kware.apps.manager.cetus.menu.dto.request.MenuSave;
import kware.apps.manager.cetus.menu.dto.request.MenuSessionTreeListSearch;
import kware.apps.manager.cetus.menu.dto.request.MenuTreeSearch;
import kware.apps.manager.cetus.menu.dto.response.MenuTreeList;
import kware.common.config.auth.MenuManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CetusMenuInfoService {

    private final CetusMenuInfoDao dao;
    private final MenuManager menuManager;

    /*
    * 로그인 시점 > 세션에 담기 위한 메뉴 조회
    * */
    @Transactional(readOnly = true)
    public List<CetusMenuInfo> getSessionMenuList(MenuSessionTreeListSearch search) {
        return dao.list(search);
    }

    @Transactional(readOnly = true)
    public List<MenuTreeList> getMenuTreeList(MenuTreeSearch search) {
        search.setWorkplaceUid(UserUtil.getUserWorkplaceUid());
        return dao.findMenuTreeList(search);
    }

    @Transactional(readOnly = true)
    public Long getRootMenuNo(String authorCd, String rootMenuCd, Long workplaceUid) {
        CetusMenuInfo cetusMenuInfo = new CetusMenuInfo(authorCd, rootMenuCd, workplaceUid);
        return dao.getRootMenuNo(cetusMenuInfo);
    }

    @Transactional
    public void saveMenu(MenuSave request) {
        dao.insert(new CetusMenuInfo(request, UserUtil.getUserWorkplaceUid()));
    }

    @Transactional
    public void changeMenu(Long menuNo, MenuChange request) {
        CetusMenuInfo view = dao.view(menuNo);
        dao.update(view.changeMenu(menuNo, request));
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
