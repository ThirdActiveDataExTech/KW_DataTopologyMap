package kware.apps.thirdeye.approveddataset.web;


import kware.apps.thirdeye.approveddataset.service.CetusDatasetService;
import kware.common.config.auth.menu.MenuNavigationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/approved/dataset")
public class CetusApprovedDatasetController {

    private final CetusDatasetService service;
    private final MenuNavigationManager menuNavigationManager;

    @GetMapping
    public String index(Model model) {
        menuNavigationManager.renderingPage("/admin/approved/dataset", "데이터 승인 관리", true, model);
        return "admin/approved/index";
    }
}
