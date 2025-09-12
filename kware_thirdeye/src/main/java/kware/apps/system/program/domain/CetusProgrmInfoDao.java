package kware.apps.system.program.domain;

import cetus.dao.SuperDao;
import kware.apps.system.program.dto.request.ProgrmFullInfoSearch;
import kware.apps.system.program.dto.response.ProgrmFullInfo;
import org.springframework.stereotype.Component;

@Component
public class CetusProgrmInfoDao extends SuperDao<CetusProgrmInfo> {

    public CetusProgrmInfoDao() {
        super("cetusProgrmInfo");
    }

    public CetusProgrmInfo getProgramByUrl(ProgrmFullInfoSearch fullInfoSearch) {
        return selectOne("getProgramByUrl", fullInfoSearch);
    }

    public ProgrmFullInfo getProgramFullInfoByUrl(ProgrmFullInfoSearch fullInfoSearch) {
        return selectOne("getProgramFullInfoByUrl", fullInfoSearch);
    }
}
