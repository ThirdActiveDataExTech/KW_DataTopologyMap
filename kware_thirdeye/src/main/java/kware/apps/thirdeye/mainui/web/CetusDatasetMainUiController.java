package kware.apps.thirdeye.mainui.web;


import kware.apps.thirdeye.mainui.dto.response.MainUiView;
import kware.apps.thirdeye.mainui.service.CetusDatasetMainUiService;
import kware.common.config.auth.menu.MenuNavigationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/main-ui")
@RequiredArgsConstructor
public class CetusDatasetMainUiController {

    private final CetusDatasetMainUiService service;
    private final MenuNavigationManager menuNavigationManager;

    @GetMapping
    public String index(Model model) {
        menuNavigationManager.renderingPage("/admin/main-ui", "데이터셋 메인UI 관리", true, model);
        return "admin/mainui/index";
    }

    @GetMapping("/save")
    public String save(Model model) {
        menuNavigationManager.renderingPage("/admin/main-ui", "데이터셋 메인UI 등록", false, model);
        return "admin/mainui/save";
    }

    @GetMapping("/form/{uid}")
    public String form(@PathVariable("uid") Long uid, Model model) {
        menuNavigationManager.renderingPage("/admin/main-ui", "데이터셋 메인UI 수정", false, model);

        Integer datasetMainUiUse = service.countDatasetMainUiUse(uid);
        model.addAttribute("datasetMainUiUse", datasetMainUiUse);

        MainUiView view = service.findDatasetMainUiByUid(uid);
        model.addAttribute("view", view);

        return "admin/mainui/form";
    }
}
