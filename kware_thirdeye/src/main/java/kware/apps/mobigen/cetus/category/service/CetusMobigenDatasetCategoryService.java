package kware.apps.mobigen.cetus.category.service;


import kware.apps.mobigen.cetus.category.domain.CetusMobigenDatasetCategory;
import kware.apps.mobigen.cetus.category.domain.CetusMobigenDatasetCategoryDao;
import kware.apps.mobigen.cetus.category.dto.request.SaveCategory;
import kware.apps.mobigen.cetus.category.dto.request.SearchCategory;
import kware.apps.mobigen.cetus.category.dto.response.CategoryList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CetusMobigenDatasetCategoryService {

    private final CetusMobigenDatasetCategoryDao dao;
    private final CetusMobigenCategoryService categoryService;

    @Transactional
    public void saveDatasetCategory(List<SaveCategory> requests, Long datasetUid) {
        dao.deleteAll(datasetUid);
        for (SaveCategory request: requests) {
            Long categoryUid = (request.getUid() == null) ? categoryService.saveMobigenCategory(request.getCategoryNm()) : request.getUid();
            CetusMobigenDatasetCategory bean = new CetusMobigenDatasetCategory(datasetUid, categoryUid);
            dao.insert(bean);
        }
    }

    @Transactional(readOnly = true)
    public List<CategoryList> findMobigenDatasetCategoryList(SearchCategory search) {
        return dao.getMobigenDatasetCategoryList(search);
    }

    @Transactional(readOnly = true)
    public List<CategoryList> findMobigenDatasetCategoryListByDatasetUid(Long datasetUid) {
        return dao.getMobigenDatasetCategoryListByDatasetUid(datasetUid);
    }
}
