package kware.apps.manager.cetus.contents.web;


import kware.apps.asp.contents.service.CetusContentsService;
import kware.apps.manager.cetus.form.dto.request.FormGroup;
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
@RequestMapping("/manager/cetus/contents")
public class CetusContentsController {

    private final CetusContentsService service;

    @GetMapping
    public String list(Model model) {
        // model.addAttribute("formGroups", FormGroup.values());
        return "manager/contents/index";
    }

    @GetMapping("/save")
    public String save(Model model) {
        // model.addAttribute("types", ElementType.values());
        // model.addAttribute("formGroups", FormGroup.values());
        return "manager/contents/save";
    }

    @GetMapping("/{uid}")
    public String form(@PathVariable Long uid, Model model) {
        // model.addAttribute("types", ElementType.values());
        model.addAttribute("content", service.view(uid));
        // model.addAttribute("uid", uid);
        return "manager/contents/form";
    }

}
