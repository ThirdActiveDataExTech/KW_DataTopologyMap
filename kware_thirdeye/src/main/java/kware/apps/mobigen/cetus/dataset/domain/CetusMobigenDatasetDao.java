package kware.apps.mobigen.cetus.dataset.domain;


import cetus.dao.SuperDao;
import kware.apps.mobigen.cetus.dataset.dto.request.SearchMobigenDataset;
import kware.apps.mobigen.integration.dto.response.metadata.MetadataView;
import kware.apps.mobigen.mobigen.dto.response.common.MetadataResultResponse;
import kware.apps.mobigen.mobigen.dto.response.relation.RelatedMetadataResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CetusMobigenDatasetDao extends SuperDao<CetusMobigenDataset> {

    public CetusMobigenDatasetDao() {
        super("cetusMobigenDataset");
    }

    public int getAllMobigenDatasetListCount() {
        return selectOne("getAllMobigenDatasetListCount");
    }
    public List<MetadataResultResponse> getAllMobigenDatasetList(SearchMobigenDataset search) {
        return selectList("getAllMobigenDatasetList", search);
    }



    public int getAllRelationMobigenDatasetListCount() {
        return selectOne("getAllRelationMobigenDatasetListCount");
    }
    public List<RelatedMetadataResponse> getAllRelationMobigenDatasetList(SearchMobigenDataset search) {
        return selectList("getAllRelationMobigenDatasetList", search);
    }




    public void deleteMobigenDataset(CetusMobigenDataset bean) {
        update("deleteMobigenDataset", bean);
    }

    public MetadataView getMobigenDatasetByDatasetId(Long datasetUid) {
        return selectOne("getMobigenDatasetByDatasetId", datasetUid);
    }

    public void ifExistDeleteApprovedDataset(Long datasetId) {
        update("ifExistDeleteApprovedDataset", datasetId);
    }
}
