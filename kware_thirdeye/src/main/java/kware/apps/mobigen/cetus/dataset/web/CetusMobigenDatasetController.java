package kware.apps.mobigen.cetus.dataset.web;


import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetView;
import kware.apps.mobigen.cetus.dataset.service.CetusMobigenDatasetService;
import kware.common.config.auth.menu.MenuNavigationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/mobigen/dataset")
public class CetusMobigenDatasetController {

    private final CetusMobigenDatasetService service;
    private final MenuNavigationManager menuNavigationManager;
    
    @GetMapping
    public String index(Model model) {
        menuNavigationManager.renderingPage("/admin/mobigen/dataset", "데이터 관리", true, model);
        return "admin/mobigen/index";
    }

    @GetMapping("/save")
    public String save(Model model) {
        menuNavigationManager.renderingPage("/admin/mobigen/dataset", "데이터 등록", false, model);
        return "admin/mobigen/save";
    }

    @GetMapping("/{uid}")
    public String form(@PathVariable("uid") Long uid, Model model) {
        menuNavigationManager.renderingPage("/admin/mobigen/dataset", "데이터 수정", false, model);
        MobigenDatasetView view = service.findMobigenDatasetByDatasetId(uid);
        model.addAttribute("view", view);
        return "admin/mobigen/form";
    }
}
