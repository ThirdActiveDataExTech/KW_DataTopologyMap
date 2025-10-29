package kware.apps.thirdeye.mobigen.mobigenregistrant.domain;


import cetus.dao.SuperDao;
import kware.apps.thirdeye.mobigen.mobigenregistrant.dto.response.MobigenRegistrantView;
import org.springframework.stereotype.Component;

@Component
public class CetusMobigenRegistrantDao extends SuperDao<CetusMobigenRegistrant> {

    public CetusMobigenRegistrantDao() {
        super("cetusMobigenRegistrant");
    }

    public MobigenRegistrantView getMobigenRegistrant(Long metadataId) {
        return selectOne("getMobigenRegistrant", metadataId);
    }
}
