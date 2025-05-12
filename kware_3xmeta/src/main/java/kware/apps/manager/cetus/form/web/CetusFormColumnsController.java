package kware.apps.manager.cetus.form.web;


import kware.apps.manager.cetus.form.dto.response.ElementType;
import kware.apps.manager.cetus.form.service.CetusFormColumnsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/asp/cetus/form")
public class CetusFormColumnsController {

    private final CetusFormColumnsService service;

    @GetMapping
    public String list() {
        return "asp/form/index";
    }

    @GetMapping("/save")
    public String save(Model model) {
        model.addAttribute("types", ElementType.values());
        return "asp/form/save";
    }

    @GetMapping("/{uid}")
    public String form(@PathVariable Long uid, Model model) {
        model.addAttribute("types", ElementType.values());
        model.addAttribute("obj", service.column(uid));
        model.addAttribute("uid", uid);
        return "asp/form/form";
    }

}
