package kware.apps.system.invite.web;

import kware.apps.system.invite.domain.CetusInvite;
import kware.apps.system.invite.domain.CetusInviteDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/thirdeye/invite")
@RequiredArgsConstructor
public class CetusInviteController {
    private final CetusInviteDao dao;

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token, HttpSession session) {
        CetusInvite invite = dao.getInviteByToken(token);

        // 만료기간 확인
        if (invite == null || invite.getExpirationDate().isBefore(LocalDateTime.now())) {
            return "redirect:/thirdeye/expired";
        }

        // 가장 최근 코드인지 확인
        CetusInvite latestInvite = dao.getLatestInviteByEmail(invite.getEmail());
        if (!latestInvite.getUrl().equals(token)) {
            return "redirect:/thirdeye/expired";
        }

        dao.activateInvite(token);
        session.setAttribute("inviteToken", token);
        session.setAttribute("isInvited", true);
        session.setAttribute("inviteEmail", invite.getEmail());
        return "redirect:/thirdeye/signup";
    }
}
