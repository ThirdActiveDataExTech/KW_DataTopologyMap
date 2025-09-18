package kware.apps.system.workplace.web;


import cetus.user.UserUtil;
import kware.apps.system.workplace.service.CetusWorkplaceService;
import kware.common.config.auth.menu.MenuNavigationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/system/workplace")
public class CetusWorkplaceController {

    private final CetusWorkplaceService service;
    private final MenuNavigationManager menuNavigationManager;

    @GetMapping
    public String index(Model model) {
        menuNavigationManager.renderingPage("/system/workplace", "워크플레이스 관리", true, model);
        model.addAttribute("workplace", UserUtil.getUserWorkplaceUid());
        return "system/workplace/index";
    }
}
