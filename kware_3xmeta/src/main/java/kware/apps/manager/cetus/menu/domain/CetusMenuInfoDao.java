package kware.apps.manager.cetus.menu.domain;

import cetus.dao.SuperDao;
import kware.apps.manager.cetus.menu.dto.request.MenuSessionTreeListSearch;
import kware.apps.manager.cetus.menu.dto.request.MenuTreeSearch;
import kware.apps.manager.cetus.menu.dto.response.MenuTreeList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CetusMenuInfoDao extends SuperDao<CetusMenuInfo> {

	public CetusMenuInfoDao() {
		super("cetusMenuInfo");
	}

	public List<MenuTreeList> findMenuTreeList(MenuTreeSearch search) {
		return selectList("findMenuTreeList", search);
	}

	public Long getRootMenuNo(CetusMenuInfo bean) {
		return selectOne("getRootMenuNo", bean);
	}

}
