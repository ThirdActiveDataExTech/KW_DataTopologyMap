package kware.apps.superrole.workplace.web;

import kware.apps.system.workplace.service.CetusWorkplaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/super")
@PreAuthorize("isAuthenticated() and principal.super")
public class CetusWorkplaceSuperController {

    private final CetusWorkplaceService workplaceService;


    @GetMapping
    public String index(Model model) {
        model.addAttribute("workplaceList", workplaceService.findWorkplaceList());
        return "super/workplace/index";
    }

    @GetMapping("/workplace")
    public String addWorkplace(Model model) {
        return "super/workplace/save";
    }
}
