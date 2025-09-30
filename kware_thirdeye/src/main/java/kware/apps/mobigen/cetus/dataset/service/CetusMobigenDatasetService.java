package kware.apps.mobigen.cetus.dataset.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.mobigen.cetus.dataset.domain.CetusMobigenDatasetDao;
import kware.apps.mobigen.cetus.dataset.dto.request.SearchMobigenDataset;
import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CetusMobigenDatasetService {

    private final CetusMobigenDatasetDao dao;

    public Page<MobigenDatasetList> findAllMobigenDatasetPage(SearchMobigenDataset search, Pageable pageable) {
        return dao.page("getAllMobigenDatasetPage", "getAllMobigenDatasetPageCount", search, pageable);
    }
}
