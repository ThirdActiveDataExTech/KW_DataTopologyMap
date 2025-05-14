package kware.apps.manager.cetus.bbs.web;


import kware.apps.manager.cetus.bbs.service.CetusBbsService;
import kware.apps.manager.cetus.enumstatus.BbsTpCd;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manager/cetus/bbs")
public class CetusBbsController {

    private final CetusBbsService service;

    @GetMapping
    public String index() {
        return "manager/bbs/index";
    }

    @GetMapping("/save")
    public String save(Model model) {
        model.addAttribute("bbsTpCds", BbsTpCd.toList());
        return "manager/bbs/save";
    }

    @GetMapping("/{uid}")
    public String form(@PathVariable("uid") Long uid, Model model) {
        model.addAttribute("bbsTpCds", BbsTpCd.toList());
        model.addAttribute("form", service.view(uid));
        return "manager/bbs/form";
    }
}
