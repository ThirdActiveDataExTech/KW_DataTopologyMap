package kware.common.config.auth;

import kware.apps.manager.cetus.menu.domain.CetusMenu;
import kware.apps.manager.cetus.menu.domain.CetusMenuInfo;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class MenuManager {
    public List<CetusMenu> parseInfoListToTreeList(List<CetusMenuInfo> allMenus, Long root) {
        long rootMenuNo = findRootMenuNo(allMenus, root);
        Stream<CetusMenuInfo> rootMenus = findRootMenus(allMenus, rootMenuNo);
        Map<Long, CetusMenu> depthMenus = getDepthMenus(allMenus);
        return rootMenus.map(rootMenu -> depthMenus.get(rootMenu.getMenuNo())).collect(Collectors.toList());
    }

    private long findRootMenuNo(List<CetusMenuInfo> allMenus, Long root) {
        Optional<CetusMenuInfo> rootMenu = allMenus.stream()
                .filter(menuInfo -> menuInfo.getUpperMenuNo() == null)
                .findFirst();
        return rootMenu.isPresent() ? rootMenu.get().getMenuNo() : root;
    }

    private boolean isRootMenu(CetusMenuInfo menuInfo, long rootMenuNo) {
        return menuInfo.getUpperMenuNo() != null && menuInfo.getUpperMenuNo().equals(rootMenuNo);
    }

    private Stream<CetusMenuInfo> findRootMenus(List<CetusMenuInfo> allMenus, long rootMenuNo) {
        return allMenus.stream()
                .filter(menuInfo -> isRootMenu(menuInfo, rootMenuNo))
                .sorted(Comparator.comparingInt(CetusMenuInfo::getSortNoForTreeMenu));
    }

    private Map<Long, CetusMenu> getDepthMenus(List<CetusMenuInfo> allMenus) {
        Map<Long, CetusMenu> menuMap = new HashMap<>();
        allMenus.forEach(menu -> menuMap.put(menu.getMenuNo(), new CetusMenu(menu)));

        for (CetusMenuInfo parent : allMenus) {
            Long parentNo = parent.getMenuNo();
            for (CetusMenuInfo child : allMenus) {
                boolean hasParent = parentNo.equals(child.getUpperMenuNo());
                if (hasParent) {
                    menuMap.get(parent.getMenuNo()).getChildren().add(menuMap.get(child.getMenuNo()));
                }
            }
        }

        return menuMap;
    }

}
