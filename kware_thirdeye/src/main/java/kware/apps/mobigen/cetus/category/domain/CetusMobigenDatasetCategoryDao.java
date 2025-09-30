package kware.apps.mobigen.cetus.category.domain;


import cetus.dao.SuperDao;
import kware.apps.mobigen.cetus.category.dto.request.SearchCategory;
import kware.apps.mobigen.cetus.category.dto.response.CategoryList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CetusMobigenDatasetCategoryDao extends SuperDao<CetusMobigenDatasetCategory> {

    public CetusMobigenDatasetCategoryDao() {
        super("cetusMobigenDatasetCategory");
    }

    public List<CategoryList> getMobigenDatasetCategoryList(SearchCategory search) {
        return selectList("getMobigenDatasetCategoryList", search);
    }

    public List<CategoryList> getMobigenDatasetCategoryListByDatasetUid(Long datasetUid) {
        return selectList("getMobigenDatasetCategoryListByDatasetUid", datasetUid);
    }

    public void deleteAll(Long uid) {
        delete("deleteAll", uid);
    }
}
