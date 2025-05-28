package kware.apps.manager.cetus.program.domain;

import cetus.dao.SuperDao;
import kware.apps.manager.cetus.program.dto.response.ProgrmFullInfo;
import org.springframework.stereotype.Component;

@Component
public class CetusProgrmInfoDao extends SuperDao<CetusProgrmInfo> {

    public CetusProgrmInfoDao() {
        super("cetusProgrmInfo");
    }

    public CetusProgrmInfo getProgramByUrl(String url) {
        return selectOne("getProgramByUrl", url);
    }

    public ProgrmFullInfo getProgramFullInfoByUrl(String url) {
        return selectOne("getProgramFullInfoByUrl", url);
    }
}
