package kware.apps.thirdeye.mobigen.category.service;

import kware.apps.thirdeye.mobigen.category.dto.request.SearchCategory;
import kware.apps.thirdeye.mobigen.category.dto.request.SearchUsingCategory;
import kware.apps.thirdeye.mobigen.category.dto.response.CategoryList;
import kware.apps.thirdeye.mobigen.category.dto.response.CategoryListWithCount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portal/dataset-category")
public class CetusDatasetCategoryRestController {

    private final CetusDatasetCategoryService service;

    /**
     *
     * 진열등록/관리되는 데이터셋 목록 조회시 검색용으로 사용
     *
     * @api         [GET] /api/portal/dataset-category/list
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
     * 진열등록/관리되는 데이터셋 목록 조회시 검색용으로 사용 + 카테고리 사용 개수 함께 조회
     *
     * @api         [GET] /api/portal/dataset-category/list-count
     * @author      dahyeon
     * @date        2025-10-15
     **/
    @GetMapping("/list-count")
    public ResponseEntity findDatasetCategoryListWithCount(SearchCategory search) {
        List<CategoryListWithCount> list = service.findDatasetCategoryListWithCount(search);
        return ResponseEntity.ok(list);
    }

    /**
     *
     * 진열등록/관리되는 데이터셋 목록 중에서 { show=Y, delete=N } 상태인 데이터셋들의 카테고리 목록 조회
     *
     * @api         [GET] /api/portal/dataset-category/using/list
     * @author      dahyeon
     * @date        2025-10-15
    **/
    @GetMapping("/using/list")
    public ResponseEntity findDatasetCategoryUsingList(SearchUsingCategory search) {
        List<CategoryList> list = service.findDatasetCategoryUsingList(search);
        return ResponseEntity.ok(list);
    }
}
