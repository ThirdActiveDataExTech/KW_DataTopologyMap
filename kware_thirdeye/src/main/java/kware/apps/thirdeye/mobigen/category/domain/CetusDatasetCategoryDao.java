package kware.apps.thirdeye.mobigen.category.domain;

import cetus.dao.SuperDao;
import kware.apps.thirdeye.mobigen.category.dto.request.SearchCategory;
import kware.apps.thirdeye.mobigen.category.dto.response.CategoryList;
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
}
