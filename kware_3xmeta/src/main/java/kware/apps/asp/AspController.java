package kware.apps.asp;


import cetus.user.UserUtil;
import kware.apps.manager.cetus.user.service.CetusUserService;
import kware.common.config.auth.dto.SessionUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
* @fileName AspController
* @author dahyeon
* @version 1.0.0
* @date 2025-01-13
* @summary  urdan 템플릿 붙이기 위한 테스트용 컨트롤러
**/

@Controller
@RequestMapping("/asp")
@RequiredArgsConstructor
public class AspController {

    private final CetusUserService cetusUserService;

    /**
     * # 컨트롤러 내에서 모든 메서드가 실행되기 전에 호출되어 기본적인 모델 데이터를 자동으로 모델에 추가한다.
     *   각각의 html 페이지에서 ${title1} ${title2} ${leftImg} ${rightImg} 와 같이 값을 불러 사용이 가능하다.
     *   -> 만약 각각의 메소드에서 값을 변경해서 보내고 싶다면 아래 [home] 메소드와 같이 진행한다.
     * */
    @ModelAttribute
    public void mainImageTitleInfo(Model model) {
        model.addAttribute("title1", "AI기반 해양수산 지능형 플랫폼");
        model.addAttribute("title2", "");
        model.addAttribute("leftImg", "");
        model.addAttribute("rightImg", "");
    }


    @GetMapping("/detail")
    public String openDetail() {
        return "asp/page/detail";
    }


    @GetMapping({"/home", "/", ""})
    public String home(Model model) {
        /*model.addAttribute("title1", "title1 수정합니다요");*/
        return "asp/page/home";
    }


    @GetMapping("/list")
    public String list() {
        return "asp/page/list";
    }


    @GetMapping("/myInfo")
    public String myInfo(Model model) {
        SessionUserInfo user = UserUtil.getUser();
        model.addAttribute("view", cetusUserService.view(user.getUid()));
        return "asp/myInfo/index";
    }

    @GetMapping("/chart")
    public String chart() {
        return "asp/page/chart";
    }

    @GetMapping("/signup")
    public String signup(HttpSession session, Model model) {
        Boolean isInvited = (Boolean) session.getAttribute("isInvited");
        String inviteToken = (String) session.getAttribute("inviteToken");

        model.addAttribute("isInvited", isInvited != null && isInvited);
        model.addAttribute("inviteToken", inviteToken);

        return "asp/page/signup";
    }

    @GetMapping("/expired")
    public String expired() {
        return "asp/page/expired";
    }
}
