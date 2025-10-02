package kware.apps.mobigen.cetus.category.service;


import jdk.jfr.Category;
import kware.apps.mobigen.cetus.category.domain.CetusMobigenCategory;
import kware.apps.mobigen.cetus.category.domain.CetusMobigenCategoryDao;
import kware.apps.mobigen.cetus.category.dto.response.CategoryList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CetusMobigenCategoryService {

    private final CetusMobigenCategoryDao dao;

    @Transactional
    public Long saveMobigenCategory(String categoryNm) {
        CetusMobigenCategory bean = new CetusMobigenCategory(categoryNm);
        dao.insert(bean);
        return bean.getUid();
    }

    @Transactional(readOnly = true)
    public List<CategoryList> findMobigenCategoryList() {
        return dao.getMobigenCategoryList();
    }
}
