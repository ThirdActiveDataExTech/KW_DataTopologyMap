package kware.apps.thirdeye.mobigen.datasetuihistory.web;

import kware.apps.thirdeye.mobigen.datasetuihistory.service.CetusDatasetHistoryService;
import kware.common.config.auth.menu.MenuNavigationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/dataset/mainui-history")
public class CetusDatasetMainUiHistoryController {

    private final CetusDatasetHistoryService service;
    private final MenuNavigationManager menuNavigationManager;

    @GetMapping("/view/{approvedUid}")
    public String view(@PathVariable("approvedUid") Long approvedUid, Model model) {
        menuNavigationManager.renderingPage("/admin/dataset/mainui-history", "메인UI 변경 이력", true, model);
        return "admin/mainuihistory/view";
    }

}
