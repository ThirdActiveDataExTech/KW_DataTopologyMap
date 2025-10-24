package kware.apps.mobigen.cetus.dataset.domain;


import cetus.dao.SuperDao;
import kware.apps.mobigen.cetus.dataset.dto.request.SearchMobigenDataset;
import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetView;
import kware.apps.mobigen.mobigen.dto.response.common.MetadataResultResponse;
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

    public void deleteMobigenDataset(CetusMobigenDataset bean) {
        update("deleteMobigenDataset", bean);
    }

    public MobigenDatasetView getMobigenDatasetByDatasetId(Long datasetUid) {
        return selectOne("getMobigenDatasetByDatasetId", datasetUid);
    }

    public void ifExistDeleteApprovedDataset(Long datasetId) {
        update("ifExistDeleteApprovedDataset", datasetId);
    }
}
