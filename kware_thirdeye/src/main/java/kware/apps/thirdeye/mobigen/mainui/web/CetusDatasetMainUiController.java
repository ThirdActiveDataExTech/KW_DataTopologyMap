package kware.apps.thirdeye.mobigen.mainui.web;


import kware.apps.thirdeye.mobigen.datasetui.service.CetusDatasetUiService;
import kware.apps.thirdeye.mobigen.mainui.domain.DatasetMainUiType;
import kware.apps.thirdeye.mobigen.mainui.dto.response.MainUiView;
import kware.apps.thirdeye.mobigen.mainui.service.CetusDatasetMainUiService;
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
    private final CetusDatasetUiService datasetUiService;

    @GetMapping
    public String index(Model model) {
        menuNavigationManager.renderingPage("/admin/main-ui", "데이터셋 메인UI 관리", true, model);
        return "admin/mainui/index";
    }

    @GetMapping("/save")
    public String save(Model model) {
        menuNavigationManager.renderingPage("/admin/main-ui", "데이터셋 메인UI 등록", false, model);
        model.addAttribute("uiType", DatasetMainUiType.toMap());
        return "admin/mainui/save";
    }

    @GetMapping("/form/{uid}")
    public String form(@PathVariable("uid") Long uid, Model model) {
        menuNavigationManager.renderingPage("/admin/main-ui", "데이터셋 메인UI 수정", false, model);

        Integer datasetMainUiUse = datasetUiService.countDatasetMainUiUse(uid);
        model.addAttribute("datasetMainUiUse", datasetMainUiUse);

        MainUiView view = service.findDatasetMainUiByUid(uid);
        model.addAttribute("view", view);

        model.addAttribute("uiType", DatasetMainUiType.toMap());

        return "admin/mainui/form";
    }
}
