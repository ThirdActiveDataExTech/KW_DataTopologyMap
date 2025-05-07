package kware.apps.manager.cetus.menu.domain;

import cetus.dao.SuperDao;
import org.springframework.stereotype.Component;

@Component
public class CetusMenuInfoDao extends SuperDao<CetusMenuInfo> {

	public CetusMenuInfoDao() {
		super("cetusMenuInfo");
	}

}
