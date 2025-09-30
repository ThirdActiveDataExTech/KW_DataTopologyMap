package kware.apps.mobigen.cetus.category.service;


import kware.apps.mobigen.cetus.category.domain.CetusMobigenCategory;
import kware.apps.mobigen.cetus.category.domain.CetusMobigenCategoryDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
