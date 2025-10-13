package kware.apps.mobigen.cetus.category.service;


import jdk.jfr.Category;
import kware.apps.mobigen.cetus.category.domain.CetusMobigenCategory;
import kware.apps.mobigen.cetus.category.domain.CetusMobigenCategoryDao;
import kware.apps.mobigen.cetus.category.dto.response.CategoryList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CetusMobigenCategoryService {

    private final CetusMobigenCategoryDao dao;

    /**
     * @method      saveMobigenCategory
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [Mobigen] 카테고리 정보 저장
    **/
    @Transactional
    public Long saveMobigenCategory(String categoryNm) {
        log.info(">>> [Mobigen] 카테고리 정보 저장");
        CetusMobigenCategory bean = new CetusMobigenCategory(categoryNm);
        dao.insert(bean);
        return bean.getUid();
    }

    /**
     * @method      findMobigenCategoryList
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription [Mobigen] 카테고리 목록 조회
    **/
    @Transactional(readOnly = true)
    public List<CategoryList> findMobigenCategoryList() {
        log.info(">>> [Mobigen] 카테고리 목록 조회");
        return dao.getMobigenCategoryList();
    }
}
