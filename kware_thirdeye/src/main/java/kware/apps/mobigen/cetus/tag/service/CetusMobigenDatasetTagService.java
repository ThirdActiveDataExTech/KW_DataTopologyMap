package kware.apps.mobigen.cetus.tag.service;


import kware.apps.mobigen.cetus.tag.domain.CetusMobigenDatasetTag;
import kware.apps.mobigen.cetus.tag.domain.CetusMobigenDatasetTagDao;
import kware.apps.mobigen.cetus.tag.dto.request.SaveTag;
import kware.apps.mobigen.cetus.tag.dto.request.SearchTag;
import kware.apps.mobigen.cetus.tag.dto.response.TagList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CetusMobigenDatasetTagService {

    private final CetusMobigenDatasetTagDao dao;
    private final CetusMobigenTagService tagService;

    @Transactional
    public void saveDatasetTag(List<SaveTag> requests, Long datasetUid) {
        dao.deleteAll(datasetUid);
        for (SaveTag request: requests) {
            Long tagUid = (request.getUid() == null) ? tagService.saveMobigenTag(request.getTagNm()) : request.getUid();
            CetusMobigenDatasetTag bean = new CetusMobigenDatasetTag(datasetUid, tagUid);
            dao.insert(bean);
        }
    }

    @Transactional(readOnly = true)
    public List<TagList> findMobigenDatasetTagList(SearchTag search) {
        return dao.getMobigenDatasetTagList(search);
    }

    @Transactional(readOnly = true)
    public List<TagList> findMobigenDatasetTagListByDatasetId(Long datasetUid) {
        return dao.getMobigenDatasetTagListByDatasetId(datasetUid);
    }
}
