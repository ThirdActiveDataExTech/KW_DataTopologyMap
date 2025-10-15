package kware.apps.mobigen.cetus.tag.service;


import kware.apps.mobigen.cetus.tag.domain.CetusMobigenTag;
import kware.apps.mobigen.cetus.tag.domain.CetusMobigenTagDao;
import kware.apps.mobigen.cetus.tag.dto.response.TagList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CetusMobigenTagService {

    private final CetusMobigenTagDao dao;

    /**
     * @method      saveMobigenTag
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [Mobigen] 태그 정보 저장
    **/
    @Transactional
    public Long saveMobigenTag(String tagNm) {
        CetusMobigenTag bean = new CetusMobigenTag(tagNm);
        dao.insert(bean);
        return bean.getUid();
    }

    /**
     * @method      findMobigenTagList
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [Mobigen] 태그 목록 조회
    **/
    @Transactional(readOnly = true)
    public List<TagList> findMobigenTagList() {
        return dao.getMobigenTagList();
    }
}
