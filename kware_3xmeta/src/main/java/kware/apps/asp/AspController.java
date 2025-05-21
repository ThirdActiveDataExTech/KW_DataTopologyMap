package kware.apps.asp;


import cetus.user.UserUtil;
import cetus.util.DateTimeUtil;
import cetus.util.HtmlUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kware.apps.manager.cetus.bbsctt.dto.response.BbscttRecentList;
import kware.apps.manager.cetus.bbsctt.service.CetusBbscttService;
import kware.apps.manager.cetus.form.service.CetusFormColumnsService;
import kware.apps.manager.cetus.user.dto.response.UserFullInfo;
import kware.apps.manager.cetus.user.service.CetusUserService;
import kware.common.config.auth.dto.SessionUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final CetusBbscttService bbscttService;
    private final CetusFormColumnsService columnsService;

    /**
     * # 컨트롤러 내에서 모든 메서드가 실행되기 전에 호출되어 기본적인 모델 데이터를 자동으로 모델에 추가한다.
     *   각각의 html 페이지에서 ${title1} ${title2} ${leftImg} ${rightImg} 와 같이 값을 불러 사용이 가능하다.
     *   -> 만약 각각의 메소드에서 값을 변경해서 보내고 싶다면 아래 [home] 메소드와 같이 진행한다.
     * */
    @ModelAttribute
    public void mainImageTitleInfo(Model model) {
        model.addAttribute("title1", "써드파티 데이터 활용 통합 플랫폼");
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
        List<BbscttRecentList> recentBbsctt = bbscttService.findRecentBbsctt(5);
        recentBbsctt.forEach(recent -> {
            // 1. 게시글 내용
            String str = HtmlUtil.stripHtmlPreserveLines(recent.getBbscttCnt());
            String customCnt = (str.length() <= 70) ? str : str.substring(0, 70) + "...";
            recent.setBbscttCnt(customCnt);

            // 2. 날짜
            Map<String, String> map = DateTimeUtil.dateToEng(recent.getRegDt());
            recent.setMonthDay(map.get("month"), map.get("day"));
        });
        model.addAttribute("recentBbsctt", recentBbsctt);
        return "asp/page/home";
    }


    @GetMapping("/list")
    public String list() {
        return "asp/page/list";
    }


    @GetMapping("/myInfo")
    public String myInfo(Model model) {
        SessionUserInfo user = UserUtil.getUser();
        UserFullInfo info = cetusUserService.findUserFullInfoByUserUid(user.getUid());
        model.addAttribute("view", info);

        model.addAttribute("fields", columnsService.getFormGroupColumns("SIGNUP"));

        String metaData = info.getMetaData();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        try {
            if (metaData != null && !metaData.trim().isEmpty()) {
                map = objectMapper.readValue(metaData, new TypeReference<Map<String, Object>>() {});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("metadata", map);

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
        String inviteEmail = (String) session.getAttribute("inviteEmail");

        model.addAttribute("isInvited", isInvited != null && isInvited);
        model.addAttribute("inviteToken", inviteToken);
        model.addAttribute("inviteEmail", inviteEmail);
        model.addAttribute("fields", columnsService.getFormGroupColumns("SIGNUP"));

        return "/signup";
    }

    @GetMapping("/expired")
    public String expired() {
        return "asp/page/expired";
    }
}
