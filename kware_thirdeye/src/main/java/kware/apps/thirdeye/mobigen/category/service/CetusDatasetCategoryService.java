package kware.apps.thirdeye.mobigen.category.service;


import cetus.user.UserUtil;
import kware.apps.thirdeye.mobigen.category.domain.CetusDatasetCategory;
import kware.apps.thirdeye.mobigen.category.domain.CetusDatasetCategoryDao;
import kware.apps.thirdeye.mobigen.category.dto.request.SaveCategory;
import kware.apps.thirdeye.mobigen.category.dto.request.SearchCategory;
import kware.apps.thirdeye.mobigen.category.dto.request.SearchUsingCategory;
import kware.apps.thirdeye.mobigen.category.dto.response.CategoryList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CetusDatasetCategoryService {

    private final CetusDatasetCategoryDao dao;

    @Transactional(readOnly = true)
    public List<CategoryList> findDatasetCategoryList(SearchCategory search) {
        search.setWorkplaceUid(UserUtil.getUserWorkplaceUid());
        return dao.getDatasetCategoryList(search);
    }

    /**
     * @method          findDatasetCategoryUsingList
     * @author          dahyeon
     * @date            2025-10-15
     * @deacription     KWARE 포탈 시스템에서 진열등록/관리 중인 데이터셋들 중에서,
     *                  { show=Y, delete=N } 상태이고, 같은 화면 UI 정보들로 구성되는 데이터셋들의 카테고리 목록 조회
    **/
    @Transactional(readOnly = true)
    public List<CategoryList> findDatasetCategoryUsingList(SearchUsingCategory search) {
        search.setWorkplaceUid(UserUtil.getUserWorkplaceUid());
        return dao.getDatasetCategoryUsingList(search);
    }

    @Transactional
    public Long saveDatasetCategory(SaveCategory request) {
        CetusDatasetCategory bean = new CetusDatasetCategory(request, UserUtil.getUserWorkplaceUid());
        dao.insert(bean);
        return bean.getUid();
    }
}
