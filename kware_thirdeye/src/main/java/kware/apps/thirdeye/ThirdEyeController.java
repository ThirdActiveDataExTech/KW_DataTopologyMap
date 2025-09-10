package kware.apps.thirdeye;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/thirdeye")
public class ThirdEyeController {

    @GetMapping("/signup")
    public String signup(HttpSession session, Model model) {
        Boolean isInvited = (Boolean) session.getAttribute("isInvited");
        String inviteToken = (String) session.getAttribute("inviteToken");
        String inviteEmail = (String) session.getAttribute("inviteEmail");

        model.addAttribute("isInvited", isInvited != null && isInvited);
        model.addAttribute("inviteToken", inviteToken);
        model.addAttribute("inviteEmail", inviteEmail);
//        model.addAttribute("fields", columnsService.getFormGroupColumns("SIGNUP"));

        return "signup";
    }

    @GetMapping("/expired")
    public String expired() {
        return "asp/page/expired";
    }
}
