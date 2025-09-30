package kware.apps.mobigen.cetus.category.domain;


import cetus.dao.SuperDao;
import org.springframework.stereotype.Component;

@Component
public class CetusMobigenCategoryDao extends SuperDao<CetusMobigenCategory> {

    public CetusMobigenCategoryDao() {
        super("cetusMobigenCategory");
    }
}
