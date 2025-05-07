package kware.common.config.auth;


import kware.apps.manager.cetus.menu.domain.CetusMenu;
import kware.apps.manager.cetus.menu.dto.request.MenuListSearch;
import kware.apps.manager.cetus.menu.dto.response.MenuList;
import kware.apps.manager.cetus.menu.service.CetusMenuInfoService;
import kware.apps.manager.cetus.user.dto.response.UserFullInfo;
import kware.apps.manager.cetus.user.service.CetusUserService;
import kware.common.config.auth.dto.SessionUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final CetusUserService cetusUserService;
    private final CetusMenuInfoService cetusMenuInfoService;
    private final MenuManager menuManager;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserFullInfo user = cetusUserService.findUserByUserId(userId);
        SessionUserInfo sessionUserInfo = new SessionUserInfo(user);
        setUserPermissionsAndMenus(sessionUserInfo);
        return new PrincipalDetails(sessionUserInfo);
    }

    public void setUserPermissionsAndMenus(SessionUserInfo sessionUserInf) {
        List<MenuList> menuList = cetusMenuInfoService.getMenuList(new MenuListSearch("Y"));
        List<String> urls = menuList.stream().map(MenuList::getUrl).collect(Collectors.toList());
        List<CetusMenu> menus = menuManager.parseInfoListToTreeList(menuList);
        sessionUserInf.addMenuAndAuthorizedMenuUrls(menus, urls);
    }
}
