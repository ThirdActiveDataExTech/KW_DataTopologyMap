package kware.apps.mobigen.cetus.dataset.domain;


import cetus.dao.SuperDao;
import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetView;
import org.springframework.stereotype.Component;

@Component
public class CetusMobigenDatasetDao extends SuperDao<CetusMobigenDataset> {

    public CetusMobigenDatasetDao() {
        super("cetusMobigenDataset");
    }

    public void deleteMobigenDataset(CetusMobigenDataset bean) {
        update("deleteMobigenDataset", bean);
    }

    public MobigenDatasetView getMobigenDatasetByDatasetId(Long datasetUid) {
        return selectOne("getMobigenDatasetByDatasetId", datasetUid);
    }
}
