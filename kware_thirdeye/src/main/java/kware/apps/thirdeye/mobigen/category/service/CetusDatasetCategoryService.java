package kware.apps.thirdeye.mobigen.category.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.user.UserUtil;
import groovy.lang.Delegate;
import io.micrometer.core.instrument.search.Search;
import kware.apps.thirdeye.mobigen.category.domain.CetusDatasetCategory;
import kware.apps.thirdeye.mobigen.category.domain.CetusDatasetCategoryDao;
import kware.apps.thirdeye.mobigen.category.dto.request.*;
import kware.apps.thirdeye.mobigen.category.dto.response.CategoryList;
import kware.apps.thirdeye.mobigen.category.dto.response.CategoryListPage;
import kware.apps.thirdeye.mobigen.datasetui.service.CetusDatasetUiService2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CetusDatasetCategoryService {

    private final CetusDatasetCategoryDao dao;
    private final CetusDatasetUiService2 uiService2;

    @Transactional(readOnly = true)
    public List<CategoryList> findDatasetCategoryList(SearchCategory search) {
        search.setWorkplaceUid(UserUtil.getUserWorkplaceUid());
        return dao.getDatasetCategoryList(search);
    }

    @Transactional(readOnly = true)
    public Page<CategoryListPage> findDatasetCategoryPage(SearchPageCategory search, Pageable pageable) {
        search.setWorkplaceUid(UserUtil.getUserWorkplaceUid());
        return dao.page("getDatasetCategoryPage", "getDatasetCategoryPageCount", search, pageable);
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

    @Transactional
    public void deleteDatasetCategory(DeleteCategories request) {
        for (Long uid : request.getUids()) {
            dao.delete(uid);
        }
    }

    @Transactional
    public void cloneDatasetCategory(CloneCategory request) {
        // 1. request.getUids() 기존 해당 category_uid들을 갖는 데이터셋 목록 가져오기
        List<Long> datasetUis = dao.getDatasetCategoryForClone(request);

        // 2. 만일 새롭게 입력한 카테고리라면 추가
        Long categoryUid = (request.getCategory().getUid() == null)
                ? this.saveDatasetCategory(request.getCategory())
                : request.getCategory().getUid();

        // 3. 1번 데이터셋 목록 2번 카테고리로 업데이트
        for (Long datasetUiUid : datasetUis) {
            uiService2.changeDatasetCategory(datasetUiUid, categoryUid);
        }
    }
}
