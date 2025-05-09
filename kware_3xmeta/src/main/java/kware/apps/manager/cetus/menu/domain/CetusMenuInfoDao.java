package kware.apps.manager.cetus.menu.domain;

import cetus.dao.SuperDao;
import kware.apps.manager.cetus.menu.dto.request.MenuListSearch;
import kware.apps.manager.cetus.menu.dto.response.MenuTreeList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CetusMenuInfoDao extends SuperDao<CetusMenuInfo> {

	public CetusMenuInfoDao() {
		super("cetusMenuInfo");
	}

	public List<MenuTreeList> findMenuTreeList(MenuListSearch search) {
		return selectList("findMenuTreeList", search);
	}

}
