package kware.apps.mobigen.cetus.category.service;


import kware.apps.mobigen.cetus.category.dto.request.SearchCategory;
import kware.apps.mobigen.cetus.category.dto.response.CategoryList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/dataset-category")
public class CetusMobigenDatasetCategoryRestController {

    private final CetusMobigenDatasetCategoryService service;

    @GetMapping("/list")
    public ResponseEntity findMobigenDatasetCategoryList(SearchCategory search) {
        List<CategoryList> list = service.findMobigenDatasetCategoryList(search);
        return ResponseEntity.ok(list);
    }
}
