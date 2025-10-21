package kware.apps.mobigen.cetus.dataset.domain;


import cetus.dao.SuperDao;
import kware.apps.mobigen.cetus.dataset.dto.request.SearchMobigenDataset;
import kware.apps.mobigen.cetus.dataset.dto.response.*;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public List<MobigenDatasetList> getAllMobigenDatasetList(SearchMobigenDataset search) {
        return selectList("getAllMobigenDatasetList", search);
    }

    public MobigenDatasetRealDataView getRealDataInfoByFileId(String fileId) {
        return selectOne("getRealDataInfoByFileId", fileId);
    }

    public void ifExistDeleteApprovedDataset(DeleteApprovedDatasetIfExist request) {
        update("ifExistDeleteApprovedDataset", request);
    }

    public List<MobigenDatasetRealDataList> getRealDataList(Long datasetId) {
        return selectList("getRealDataList", datasetId);
    }

    public void deleteRealdata(String fileId) {
        update("deleteRealdata", fileId);
    }
}
