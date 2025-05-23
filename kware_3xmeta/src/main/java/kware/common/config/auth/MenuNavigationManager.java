package kware.common.config.auth;

import cetus.user.UserUtil;
import kware.apps.manager.cetus.menu.domain.CetusMenu;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MenuNavigationManager {

    public void renderingPage(String parentMenuUrl, String currentPageName, boolean isTopMenu, final Model model) {
        Map<String, String> map = new HashMap<>();

        List<CetusMenu> menusTop = UserUtil.getUser().getMenusTop();

        // 1. 부모 메뉴 찾기
        CetusMenu parentMenu = findMenuByUrl(menusTop, parentMenuUrl);
        if (parentMenu == null) {
            parentMenu = getDefaultMenu(menusTop);
        }

        // 2. 네비게이션 경로 만들기
        List<String> navPath = new ArrayList<>();
        buildNavigationPath(menusTop, parentMenuUrl, navPath);
        if (!isTopMenu) {
            navPath.add(currentPageName);
        }

        // 3. 이미지 스타일 정보 꺼내기
        String centerLeftImage = "/assets/images/page/left_slide.png";//parentMenu != null ? parentMenu.getMenuStyle1() : "";
        String centerRightImage = "/assets/images/page/right_slide.png";//parentMenu != null ? parentMenu.getMenuStyle2() : "";

        // 4. 뷰에 전달
        model.addAttribute("currentPageName", currentPageName);
        model.addAttribute("navigation", String.join(" > ", navPath));
        model.addAttribute("centerLeftImage", centerLeftImage);
        model.addAttribute("centerRightImage", centerRightImage);
    }

    private CetusMenu findMenuByUrl(List<CetusMenu> menus, String url) {
        for (CetusMenu menu : menus) {
            if (menu.getUrl().equals(url)) {
                return menu;
            }
            if (menu.hasChild()) {
                CetusMenu found = findMenuByUrl(menu.getChildren(), url);
                if (found != null) return found;
            }
        }
        return null;
    }

    private boolean buildNavigationPath(List<CetusMenu> menus, String targetUrl, List<String> path) {
        for (CetusMenu menu : menus) {
            path.add(menu.getMenuNm());
            if (menu.getUrl().equals(targetUrl)) {
                return true;
            }
            if (menu.hasChild()) {
                boolean found = buildNavigationPath(menu.getChildren(), targetUrl, path);
                if (found) return true;
            }
            path.remove(path.size() - 1);
        }
        return false;
    }

    private CetusMenu getDefaultMenu(List<CetusMenu> menus) {
        return (menus != null && !menus.isEmpty()) ? menus.get(0) : null;
    }
}
