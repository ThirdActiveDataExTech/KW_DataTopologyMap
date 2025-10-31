package kware.apps.superrole.web;


import kware.apps.system.workplace.service.CetusWorkplaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/super")
public class SuperController {

    private final CetusWorkplaceService workplaceService;


    @GetMapping
    public String index(Model model) {
        model.addAttribute("workplaceList", workplaceService.findWorkplaceList());
        return "super/index";
    }

    @GetMapping("/workplace")
    public String addWorkplace(Model model) {
        return "super/workplace-save";
    }
}
