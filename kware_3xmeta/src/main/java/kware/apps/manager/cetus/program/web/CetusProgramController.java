package kware.apps.manager.cetus.program.web;

import cetus.user.UserUtil;
import kware.apps.manager.cetus.enumstatus.UserAuthorCd;
import kware.apps.manager.cetus.program.service.CetusProgrmInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manager/cetus/program")
public class CetusProgramController {

    private final CetusProgrmInfoService service;

    @GetMapping
    public String index(Model model) {
        return "manager/program/index";
    }

    @GetMapping("/{uid}")
    public String form(@PathVariable("uid") Long uid, Model model) {
        model.addAttribute("form", service.view(uid));
        return "manager/program/form";
    }

    @GetMapping("/save")
    public String save(Model model) {
        return "manager/program/save";
    }
}
