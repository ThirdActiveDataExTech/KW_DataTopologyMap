package kware.apps.mobigen.cetus.tag.domain;

import cetus.dao.SuperDao;
import io.micrometer.core.instrument.search.Search;
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

    public List<TagList> getMobigenDatasetTagListByDatasetId(Long datasetUid) {
        return selectList("getMobigenDatasetTagListByDatasetId", datasetUid);
    }

    public void deleteAll(Long uid) {
        delete("deleteAll", uid);
    }
}
