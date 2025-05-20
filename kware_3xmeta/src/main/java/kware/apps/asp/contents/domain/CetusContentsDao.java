package kware.apps.asp.contents.domain;


import cetus.dao.SuperDao;
import kware.apps.manager.cetus.group.dto.response.GroupList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CetusContentsDao extends SuperDao<CetusCategories> {

    public CetusContentsDao() {
        super("cetusCategories");
    }

    // public List<GroupList> getGroupList(Long workplaceUid) {
    //     return selectList("getGroupList", workplaceUid);
    // }
}
