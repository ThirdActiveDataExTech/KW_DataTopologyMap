package kware.apps.mobigen.cetus.dataset.service;


import kware.apps.mobigen.cetus.dataset.domain.CetusMobigenDataset;
import kware.apps.mobigen.cetus.dataset.domain.CetusMobigenDatasetDao;
import kware.apps.mobigen.cetus.dataset.dto.request.SearchMobigenDataset;
import kware.apps.mobigen.integration.dto.response.metadata.MetadataView;
import kware.apps.mobigen.mobigen.dto.response.common.MetadataResultResponse;
import kware.apps.mobigen.mobigen.dto.response.recommendation.RecommendationsResponse;
import kware.apps.mobigen.mobigen.dto.response.relation.RelatedMetadataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CetusMobigenDatasetService {

    private final CetusMobigenDatasetDao dao;

    @Transactional
    public void insert(CetusMobigenDataset bean) {
        dao.insert(bean);
    }

    @Transactional
    public void update(CetusMobigenDataset bean) {
        dao.update(bean);
    }

    @Transactional
    public void ifExistDeleteApprovedDataset(Long datasetId) {
        dao.ifExistDeleteApprovedDataset(datasetId);
    }

    @Transactional
    public void deleteMobigenDataset(CetusMobigenDataset bean) {
        dao.deleteMobigenDataset(bean);
    }

    @Transactional(readOnly = true)
    public MetadataView findMobigenDatasetByDatasetId(Long datasetId) {
        return dao.getMobigenDatasetByDatasetId(datasetId);
    }


    @Transactional(readOnly = true)
    public int findAllMobigenDatasetListCount() {
        return dao.getAllMobigenDatasetListCount();
    }
    @Transactional(readOnly = true)
    public List<MetadataResultResponse> findAllMobigenDatasetList(SearchMobigenDataset search) {
        return dao.getAllMobigenDatasetList(search);
    }


    @Transactional(readOnly = true)
    public int findAllRelationMobigenDatasetListCount() {
        return dao.getAllRelationMobigenDatasetListCount();
    }
    @Transactional(readOnly = true)
    public List<RelatedMetadataResponse> findAllRelationMobigenDatasetList(SearchMobigenDataset search) {
        return dao.getAllRelationMobigenDatasetList(search);
    }


    @Transactional(readOnly = true)
    public int findAllRecommendationMobigenDatasetListCount() {
        return dao.getAllRecommendationMobigenDatasetListCount();
    }
    @Transactional(readOnly = true)
    public List<RecommendationsResponse> findAllRecommendationMobigenDatasetList(SearchMobigenDataset search) {
        return dao.getAllRecommendationMobigenDatasetList(search);
    }
}
