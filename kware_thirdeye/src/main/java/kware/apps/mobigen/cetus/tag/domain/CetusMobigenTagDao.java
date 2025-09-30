package kware.apps.mobigen.cetus.tag.domain;


import cetus.dao.SuperDao;
import org.springframework.stereotype.Component;

@Component
public class CetusMobigenTagDao extends SuperDao<CetusMobigenTag> {

    public CetusMobigenTagDao() {
        super("cetusMobigenTag");
    }
}
