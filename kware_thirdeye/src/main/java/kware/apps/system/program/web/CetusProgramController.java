package kware.apps.system.program.web;

import kware.apps.system.program.service.CetusProgrmInfoService;
import kware.common.config.auth.MenuNavigationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/system/program")
public class CetusProgramController {

    private final CetusProgrmInfoService service;
    private final MenuNavigationManager menuNavigationManager;

    @GetMapping
    public String index(Model model) {
        menuNavigationManager.renderingPage("/system/program", "프로그램 관리", true, model);
        return "system/program/index";
    }

    @GetMapping("/{uid}")
    public String form(@PathVariable("uid") Long uid, Model model) {
        menuNavigationManager.renderingPage("/system/program", "프로그램 수정", false, model);
        model.addAttribute("form", service.view(uid));
        return "system/program/form";
    }

    @GetMapping("/save")
    public String save(Model model) {
        menuNavigationManager.renderingPage("/system/program", "프로그램 등록", false, model);
        return "system/program/save";
    }
}
