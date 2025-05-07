package kware.common.config.auth;

import kware.apps.manager.cetus.menu.domain.CetusMenu;
import kware.apps.manager.cetus.menu.dto.response.MenuList;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class MenuManager {
    public List<CetusMenu> parseInfoListToTreeList(List<MenuList> menuList) {
        long rootMenuNo = findRootMenuNo(menuList);
        Stream<MenuList> rootMenus = findRootMenus(menuList, rootMenuNo);
        Map<Long, CetusMenu> depthMenus = getDepthMenus(menuList);
        return rootMenus.map(rootMenu -> depthMenus.get(rootMenu.getMenuNo())).collect(Collectors.toList());
    }

    private long findRootMenuNo(List<MenuList> menuList) {
        Optional<MenuList> rootMenu = menuList.stream()
                .filter(menuInfo -> menuInfo.getUpperMenuNo() == null)
                .findFirst();
        return rootMenu.isPresent() ? rootMenu.get().getMenuNo() : 1L;
    }

    private boolean isRootMenu(MenuList menu, long rootMenuNo) {
        return menu.getUpperMenuNo() != null && menu.getUpperMenuNo().equals(rootMenuNo);
    }

    private Stream<MenuList> findRootMenus(List<MenuList> menuList, long rootMenuNo) {
        return menuList.stream()
                .filter(menuInfo -> isRootMenu(menuInfo, rootMenuNo))
                .sorted(Comparator.comparingInt(MenuList::getSortNoForTreeMenu));
    }

    private Map<Long, CetusMenu> getDepthMenus(List<MenuList> menuList) {
        Map<Long, CetusMenu> menuMap = new HashMap<>();
        menuList.forEach(menu -> menuMap.put(menu.getMenuNo(), new CetusMenu(menu)));

        for (MenuList parent : menuList) {
            Long parentNo = parent.getMenuNo();
            for (MenuList child : menuList) {
                boolean hasParent = parentNo.equals(child.getUpperMenuNo());
                if (hasParent) {
                    menuMap.get(parent.getMenuNo()).getChildren().add(menuMap.get(child.getMenuNo()));
                }
            }
        }

        return menuMap;
    }

}
