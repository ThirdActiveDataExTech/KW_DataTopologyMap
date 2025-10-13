package kware.apps.thirdeye.mobigen.mobigenregistrant.domain;


import cetus.dao.SuperDao;
import org.springframework.stereotype.Component;

@Component
public class CetusMobigenRegistrantDao extends SuperDao<CetusMobigenRegistrant> {

    public CetusMobigenRegistrantDao() {
        super("cetusMobigenRegistrant");
    }
}
