package kware.apps.mobigen.cetus.tag.domain;

import cetus.dao.SuperDao;
import kware.apps.mobigen.cetus.tag.dto.request.SearchTag;
import kware.apps.mobigen.cetus.tag.dto.response.TagList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CetusMobigenDatasetTagDao extends SuperDao<CetusMobigenDatasetTag> {

    public CetusMobigenDatasetTagDao() {
        super("cetusMobigenDatasetTag");
    }

    public List<TagList> getMobigenDatasetTagList(SearchTag search) {
        return selectList("getMobigenDatasetTagList", search);
    }

    public List<TagList> getMobigenDatasetTagListByMetadataId(String metadataId) {
        return selectList("getMobigenDatasetTagListByMetadataId", metadataId);
    }

    public void deleteAll(String metadataId) {
        delete("deleteAll", metadataId);
    }
}
