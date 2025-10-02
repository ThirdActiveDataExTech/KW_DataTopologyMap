package kware.apps.mobigen.cetus.tag.service;


import kware.apps.mobigen.cetus.category.dto.response.CategoryList;
import kware.apps.mobigen.cetus.tag.domain.CetusMobigenTag;
import kware.apps.mobigen.cetus.tag.domain.CetusMobigenTagDao;
import kware.apps.mobigen.cetus.tag.dto.response.TagList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional(readOnly = true)
    public List<TagList> findMobigenTagList() {
        return dao.getMobigenTagList();
    }
}
