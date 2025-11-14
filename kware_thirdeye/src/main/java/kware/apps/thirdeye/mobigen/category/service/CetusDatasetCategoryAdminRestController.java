package kware.apps.thirdeye.mobigen.category.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.thirdeye.mobigen.category.dto.request.*;
import kware.apps.thirdeye.mobigen.category.dto.request.changesortno.ChangeCategorySortNoList;
import kware.apps.thirdeye.mobigen.category.dto.response.CategoryList;
import kware.apps.thirdeye.mobigen.category.dto.response.CategoryListPage;
import kware.apps.thirdeye.mobigen.category.dto.response.ListCategoryByMainUi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/dataset-category")
public class CetusDatasetCategoryAdminRestController {

    private final CetusDatasetCategoryService service;

    /**
     *
     * KWARE 포탈 시스템에서 > 모비젠 데이터셋 진열등록/관리 시점에 사용
     * 진열등록/관리되는 각각의 데이터셋들은 1개의 카테고리를 필수로 갖는다.
     * 따라서 KWARE 관리자는 데이터셋을 진열등록/관리하는 시점에 1개의 카테고리를 추가 입력해야 한다.
     *
     * @api         [GET] /api/admin/dataset-category/list
     * @author      dahyeon
     * @date        2025-10-15
    **/
    @GetMapping("/list")
    public ResponseEntity findDatasetCategoryList(SearchCategory search) {
        List<CategoryList> list = service.findDatasetCategoryList(search);
        return ResponseEntity.ok(list);
    }

    /**
     *
     * 카테고리 관리를 위한 카테고리 목록 페이징 조회
     *
     * @api         [GET]  /api/admin/dataset-category/page
     * @author      dahyeon
     * @date        2025-10-21
    **/
    @GetMapping("/page")
    public ResponseEntity findDatasetCategoryPage(SearchPageCategory search, Pageable pageable) {
        Page<CategoryListPage> page = service.findDatasetCategoryPage(search, pageable);
        return ResponseEntity.ok(page);
    }

    /**
     *
     * 해당 카테고리를 사용하는 데이터셋 목록이 없는 경우,
     * 해당 카테고리는 삭제할 수 있다. (물리 삭제)
     *
     * @api         [PUT] /api/admin/dataset-category/delete-several
     * @author      dahyeon
     * @date        2025-10-21
    **/
    @PutMapping("/delete-several")
    public ResponseEntity deleteDatasetCategory(@RequestBody DeleteCategories request) {
        service.deleteDatasetCategory(request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 기존에 데이터셋과 연결된 카테고리에 대해서 새로운 카테고리 혹은 다른 카테고리로 재연결 시키기
     * (ex) {female} 카테고리 하위로 데이터셋 {data_A}가 있고
     *      {여성}  카테고리 하위로 데이터셋 {data_B, data_C}가 있었을때
     *      {female, 여성} -> {여자} 카테고리로 통합한다.
     *
     *      (i) {data_A, data_B, data_C} 데이터셋의 카테고리를 새롭게 생성된 {여자}로 연결
     *
     * @api         [PUT] /api/admin/dataset-category/clone
     * @author      dahyeon
     * @date        2025-10-21
    **/
    @PutMapping("/clone")
    public ResponseEntity cloneDatasetCategory(@RequestBody CloneCategory request) {
        service.cloneDatasetCategory(request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * main_ui_uid 값을 통해 해당 하위 카테고리 목록 조회
     *
     * @api         [GET] /api/admin/dataset-category/main-ui
     * @author      dahyeon
     * @date        2025-11-14
    **/
    @GetMapping("/main-ui")
    public ResponseEntity findCategoryByMainUi(SearchCategoryByMainUi search) {
        List<ListCategoryByMainUi> list = service.findCategoryByMainUi(search);
        return ResponseEntity.ok(list);
    }

    /**
     *
     * main_ui_uid 값을 통해 해당 하위 카테고리 목록에 대해 정렬 순서 변경
     *
     * @api         [PUT] /api/admin/dataset-category/change/main-ui
     * @author      dahyeon
     * @date        2025-11-14
    **/
    @PutMapping("/change/main-ui")
    public ResponseEntity changeCategorySortNo(@RequestBody ChangeCategorySortNoList request) {
        service.changeCategorySortNo(request);
        return ResponseEntity.ok().build();
    }
}
