package kware.apps.manager.cetus.menu.service;

import java.util.List;

import kware.apps.manager.cetus.menu.domain.CetusMenuInfoDao;
import kware.apps.manager.cetus.menu.dto.request.MenuListSearch;
import kware.apps.manager.cetus.menu.dto.response.MenuList;
import kware.apps.manager.cetus.menu.dto.response.MenuTreeList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CetusMenuInfoService {

    private final CetusMenuInfoDao dao;

    @Transactional(readOnly = true)
    public List<MenuList> getMenuList(MenuListSearch search) {
        return dao.list(search);
    }

    @Transactional(readOnly = true)
    public List<MenuTreeList> getMenuTreeList(MenuListSearch search) {
        return dao.findMenuTreeList(search);
    }
}
