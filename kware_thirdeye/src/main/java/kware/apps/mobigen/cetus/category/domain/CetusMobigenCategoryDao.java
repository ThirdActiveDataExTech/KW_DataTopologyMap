package kware.apps.mobigen.cetus.category.domain;


import cetus.dao.SuperDao;
import kware.apps.mobigen.cetus.category.dto.response.CategoryList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CetusMobigenCategoryDao extends SuperDao<CetusMobigenCategory> {

    public CetusMobigenCategoryDao() {
        super("cetusMobigenCategory");
    }

    public List<CategoryList> getMobigenCategoryList() {
        return selectList("getMobigenCategoryList");
    }
}
