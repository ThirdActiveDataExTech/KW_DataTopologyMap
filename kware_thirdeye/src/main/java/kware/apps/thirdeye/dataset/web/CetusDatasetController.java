package kware.apps.thirdeye.dataset.web;


import kware.apps.thirdeye.dataset.service.CetusDatasetService;
import kware.common.config.auth.MenuNavigationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/dataset")
public class CetusDatasetController {

    private final CetusDatasetService service;
    private final MenuNavigationManager menuNavigationManager;
    
    @GetMapping
    public String index(Model model) {
        menuNavigationManager.renderingPage("/admin/dataset", "데이터 관리", true, model);
        return "admin/dataset/index";
    }

    @GetMapping("/save")
    public String save(Model model) {
        menuNavigationManager.renderingPage("/admin/dataset", "데이터 관리", false, model);
        return "admin/dataset/save";
    }
}
