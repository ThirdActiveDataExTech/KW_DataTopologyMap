package kware.apps.thirdeye.mobigen.category.web;

import kware.apps.thirdeye.mobigen.category.service.CetusDatasetCategoryService;
import kware.common.config.auth.menu.MenuNavigationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/dataset/category")
public class CetusCategoryController {

    private final CetusDatasetCategoryService service;
    private final MenuNavigationManager menuNavigationManager;

    @GetMapping
    public String index(Model model) {
        menuNavigationManager.renderingPage("/admin/dataset/category", "데이터 카테고리 관리", true, model);
        return "admin/category/index";
    }
}
