package kware.apps.thirdeye.mobigen.thirdeyeregistrant.domain;


import cetus.dao.SuperDao;
import kware.apps.thirdeye.mobigen.thirdeyeregistrant.dto.response.ThirdeyeRegistrantView;
import org.springframework.stereotype.Component;

@Component
public class CetusThirdeyeRegistrantDao extends SuperDao<CetusThirdeyeRegistrant> {

    public CetusThirdeyeRegistrantDao() {
        super("cetusThirdeyeRegistrant");
    }

    public ThirdeyeRegistrantView getThirdeyeRegistrant(String metadataId) {
        return selectOne("getThirdeyeRegistrant", metadataId);
    }
}
