package kware.apps.thirdeye.mobigen.category.domain;

import cetus.dao.SuperDao;
import kware.apps.thirdeye.mobigen.category.dto.request.CloneCategory;
import kware.apps.thirdeye.mobigen.category.dto.request.SearchCategory;
import kware.apps.thirdeye.mobigen.category.dto.request.SearchCategoryByMainUi;
import kware.apps.thirdeye.mobigen.category.dto.request.SearchUsingCategory;
import kware.apps.thirdeye.mobigen.category.dto.response.CategoryList;
import kware.apps.thirdeye.mobigen.category.dto.response.ListCategoryByMainUi;
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

    public List<CategoryList> getDatasetCategoryUsingList(SearchUsingCategory search) {
        return selectList("getDatasetCategoryUsingList", search);
    }

    public List<Long> getDatasetCategoryForClone(CloneCategory request) {
        return selectList("getDatasetCategoryForClone", request);
    }

    public List<ListCategoryByMainUi> getCategoryByMainUi(SearchCategoryByMainUi search) {
        return selectList("getCategoryByMainUi", search);
    }

    public void updateCategorySortNo(CetusDatasetCategory bean) {
        update("updateCategorySortNo", bean);
    }
}
