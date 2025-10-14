package kware.apps.thirdeye.mobigen.category.service;


import cetus.user.UserUtil;
import kware.apps.thirdeye.mobigen.category.domain.CetusDatasetCategory;
import kware.apps.thirdeye.mobigen.category.domain.CetusDatasetCategoryDao;
import kware.apps.thirdeye.mobigen.category.dto.request.SaveCategory;
import kware.apps.thirdeye.mobigen.category.dto.request.SearchCategory;
import kware.apps.thirdeye.mobigen.category.dto.response.CategoryList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CetusDatasetCategoryService {

    private final CetusDatasetCategoryDao dao;

    @Transactional
    public List<CategoryList> findDatasetCategoryList(SearchCategory search) {
        search.setWorkplaceUid(UserUtil.getUserWorkplaceUid());
        return dao.getDatasetCategoryList(search);
    }

    @Transactional(readOnly = true)
    public Long saveDatasetCategory(SaveCategory request) {
        CetusDatasetCategory bean = new CetusDatasetCategory(request, UserUtil.getUserWorkplaceUid());
        dao.insert(bean);
        return bean.getUid();
    }
}
