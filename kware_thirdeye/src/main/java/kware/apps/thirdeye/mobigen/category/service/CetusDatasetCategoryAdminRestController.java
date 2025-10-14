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

    @GetMapping("/list")
    public ResponseEntity findDatasetCategoryList(SearchCategory search) {
        List<CategoryList> list = service.findDatasetCategoryList(search);
        return ResponseEntity.ok(list);
    }
}
