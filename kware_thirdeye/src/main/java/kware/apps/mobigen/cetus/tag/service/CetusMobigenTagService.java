package kware.apps.mobigen.cetus.tag.service;


import kware.apps.mobigen.cetus.tag.domain.CetusMobigenTag;
import kware.apps.mobigen.cetus.tag.domain.CetusMobigenTagDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CetusMobigenTagService {

    private final CetusMobigenTagDao dao;

    @Transactional
    public Long saveMobigenTag(String tagNm) {
        CetusMobigenTag bean = new CetusMobigenTag(tagNm);
        dao.insert(bean);
        return bean.getUid();
    }
}
