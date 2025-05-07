package kware.apps.manager.cetus.user.web;


import kware.apps.manager.cetus.user.service.CetusUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manager/cetus/user")
public class CetusUserController {

    private final CetusUserService service;

    @GetMapping({"", "/"})
    public String index() {
        return "manager/user/index";
    }

    @GetMapping("/{uid}")
    public String form(@PathVariable("uid") Long uid) {
        return "manager/user/form";
    }
}
