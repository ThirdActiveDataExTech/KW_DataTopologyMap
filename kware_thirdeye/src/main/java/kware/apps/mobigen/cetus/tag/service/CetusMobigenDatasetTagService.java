package kware.apps.mobigen.cetus.tag.service;


import kware.apps.mobigen.cetus.tag.domain.CetusMobigenDatasetTag;
import kware.apps.mobigen.cetus.tag.domain.CetusMobigenDatasetTagDao;
import kware.apps.mobigen.cetus.tag.dto.request.SaveTag;
import kware.apps.mobigen.cetus.tag.dto.request.SearchTag;
import kware.apps.mobigen.cetus.tag.dto.response.TagList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CetusMobigenDatasetTagService {

    private final CetusMobigenDatasetTagDao dao;
    private final CetusMobigenTagService tagService;

    /**
     * @method      saveDatasetTag
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [Mobigen] 태그 & 데이터셋 매핑 정보 저장
    **/
    @Transactional
    public void saveDatasetTag(List<SaveTag> requests, Long metadataId) {
        dao.deleteAll(metadataId);
        for (SaveTag request: requests) {
            Long tagUid = (request.getUid() == null) ? tagService.saveMobigenTag(request.getTagNm()) : request.getUid();
            CetusMobigenDatasetTag bean = new CetusMobigenDatasetTag(metadataId, tagUid);
            dao.insert(bean);
        }
    }

    /**
     * @method      findMobigenDatasetTagList
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [Mobigen] 태그 & 데이터셋 매핑 목록 조회
    **/
    @Transactional(readOnly = true)
    public List<TagList> findMobigenDatasetTagList(SearchTag search) {
        return dao.getMobigenDatasetTagList(search);
    }

    /**
     * @method      findMobigenDatasetTagListByMetadataId
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [Mobigen] 태그 & 데이터셋 매핑 -> 데이터셋 기준 단건 조회
    **/
    @Transactional(readOnly = true)
    public List<TagList> findMobigenDatasetTagListByMetadataId(Long metadataId) {
        return dao.getMobigenDatasetTagListByMetadataId(metadataId);
    }
}
