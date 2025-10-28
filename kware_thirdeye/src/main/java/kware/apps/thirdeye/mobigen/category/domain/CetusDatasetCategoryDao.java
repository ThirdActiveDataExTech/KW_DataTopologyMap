package kware.apps.thirdeye.mobigen.category.domain;

import cetus.dao.SuperDao;
import kware.apps.thirdeye.mobigen.category.dto.request.CloneCategory;
import kware.apps.thirdeye.mobigen.category.dto.request.SearchCategory;
import kware.apps.thirdeye.mobigen.category.dto.request.SearchUsingCategory;
import kware.apps.thirdeye.mobigen.category.dto.response.CategoryList;
import kware.apps.thirdeye.mobigen.category.dto.response.CategoryListWithCount;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CetusDatasetCategoryDao extends SuperDao<CetusDatasetCategory> {

    public CetusDatasetCategoryDao() {
        super("cetusDatasetCategory");
    }

    public List<CategoryList> getDatasetCategoryList(SearchCategory search) {
        return selectList("getDatasetCategoryList", search);
    }

    public List<CategoryListWithCount> getDatasetCategoryListWithCount(SearchCategory search) {
        return selectList("getDatasetCategoryListWithCount", search);
    }

    public List<CategoryList> getDatasetCategoryUsingList(SearchUsingCategory search) {
        return selectList("getDatasetCategoryUsingList", search);
    }

    public List<Long> getDatasetCategoryForClone(CloneCategory request) {
        return selectList("getDatasetCategoryForClone", request);
    }
}
