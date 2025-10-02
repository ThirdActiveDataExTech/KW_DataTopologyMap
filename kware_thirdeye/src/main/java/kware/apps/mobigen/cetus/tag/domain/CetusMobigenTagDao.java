package kware.apps.mobigen.cetus.tag.domain;


import cetus.dao.SuperDao;
import kware.apps.mobigen.cetus.tag.dto.response.TagList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CetusMobigenTagDao extends SuperDao<CetusMobigenTag> {

    public CetusMobigenTagDao() {
        super("cetusMobigenTag");
    }

    public List<TagList> getMobigenTagList() {
        return selectList("getMobigenTagList");
    }
}
