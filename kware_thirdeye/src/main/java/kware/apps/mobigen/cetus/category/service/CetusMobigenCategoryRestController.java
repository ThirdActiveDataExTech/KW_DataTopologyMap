package kware.apps.mobigen.cetus.category.service;


import kware.apps.mobigen.cetus.category.dto.response.CategoryList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portal/mobigen-category")
public class CetusMobigenCategoryRestController {

    private final CetusMobigenCategoryService service;

    @GetMapping("/list")
    public ResponseEntity findMobigenCategoryList() {
        List<CategoryList> list = service.findMobigenCategoryList();
        return ResponseEntity.ok(list);
    }
}
