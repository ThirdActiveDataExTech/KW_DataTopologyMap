package kware.apps.thirdeye.mobigen.category.service;


import kware.apps.thirdeye.mobigen.category.dto.request.SearchCategory;
import kware.apps.thirdeye.mobigen.category.dto.response.CategoryList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
