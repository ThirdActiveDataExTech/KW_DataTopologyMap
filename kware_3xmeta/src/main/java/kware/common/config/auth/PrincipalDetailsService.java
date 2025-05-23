package kware.common.config.auth;


import kware.apps.manager.cetus.enumstatus.MenuRootCd;
import kware.apps.manager.cetus.menu.domain.CetusMenu;
import kware.apps.manager.cetus.menu.domain.CetusMenuInfo;
import kware.apps.manager.cetus.menu.dto.request.MenuSessionTreeListSearch;
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
import java.util.stream.Stream;

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

    public void setUserPermissionsAndMenus(SessionUserInfo sessionUserInfo) {

        String role = sessionUserInfo.getRole();
        Long workplaceUid = sessionUserInfo.getWorkplaceUid();

        Long topMenuNo = cetusMenuInfoService.getRootMenuNo(role, MenuRootCd.TOP_ROOT.name(), workplaceUid);
        Long footerMenuNo = cetusMenuInfoService.getRootMenuNo(role, MenuRootCd.FOOTER_ROOT.name(), workplaceUid);

        List<CetusMenuInfo> menuTopList = cetusMenuInfoService.getSessionMenuList(new MenuSessionTreeListSearch("Y", role, workplaceUid, topMenuNo));
        List<CetusMenuInfo> menuFooterList = cetusMenuInfoService.getSessionMenuList(new MenuSessionTreeListSearch("Y", role, workplaceUid, footerMenuNo));

        List<String> urls = Stream.concat(
                menuTopList.stream().map(CetusMenuInfo::getUrl),
                menuFooterList.stream().map(CetusMenuInfo::getUrl)
        ).collect(Collectors.toList());

        List<CetusMenu> topMenus = menuManager.parseInfoListToTreeList(menuTopList, topMenuNo);
        List<CetusMenu> footerMenus = menuManager.parseInfoListToTreeList(menuFooterList, footerMenuNo);
        sessionUserInfo.addMenuAndAuthorizedMenuUrls(topMenus, footerMenus, urls);
    }
}
