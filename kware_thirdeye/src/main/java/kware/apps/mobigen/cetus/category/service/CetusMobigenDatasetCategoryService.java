package kware.apps.mobigen.cetus.category.service;


import kware.apps.mobigen.cetus.category.domain.CetusMobigenDatasetCategory;
import kware.apps.mobigen.cetus.category.domain.CetusMobigenDatasetCategoryDao;
import kware.apps.mobigen.cetus.category.dto.request.SaveCategory;
import kware.apps.mobigen.cetus.category.dto.request.SearchCategory;
import kware.apps.mobigen.cetus.category.dto.response.CategoryList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CetusMobigenDatasetCategoryService {

    private final CetusMobigenDatasetCategoryDao dao;
    private final CetusMobigenCategoryService categoryService;

    /**
     * @method      saveDatasetCategory
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [Mobigen] 카테고리 & 데이터셋 매핑 정보 저장
    **/
    @Transactional
    public void saveDatasetCategory(List<SaveCategory> requests, Long datasetUid) {
        log.info(">>> [Mobigen] 카테고리 & 데이터셋 매핑 정보 저장");
        dao.deleteAll(datasetUid);
        for (SaveCategory request: requests) {
            Long categoryUid = (request.getUid() == null) ? categoryService.saveMobigenCategory(request.getCategoryNm()) : request.getUid();
            CetusMobigenDatasetCategory bean = new CetusMobigenDatasetCategory(datasetUid, categoryUid);
            dao.insert(bean);
        }
    }

    /**
     * @method      findMobigenDatasetCategoryList
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [Mobigen] 카테고리 & 데이터셋 매핑 목록 조회
    **/
    @Transactional(readOnly = true)
    public List<CategoryList> findMobigenDatasetCategoryList(SearchCategory search) {
        log.info(">>> [Mobigen] 카테고리 & 데이터셋 매핑 목록 조회");
        return dao.getMobigenDatasetCategoryList(search);
    }

    /**
     * @method      findMobigenDatasetCategoryListByDatasetUid
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [Mobigen] 카테고리 & 데이터셋 매핑 -> 데이터셋 기준 단건 조회
    **/
    @Transactional(readOnly = true)
    public List<CategoryList> findMobigenDatasetCategoryListByDatasetUid(Long datasetUid) {
        log.info(">>> [Mobigen] 카테고리 & 데이터셋 매핑 -> 데이터셋 기준 단건 조회");
        return dao.getMobigenDatasetCategoryListByDatasetUid(datasetUid);
    }
}
