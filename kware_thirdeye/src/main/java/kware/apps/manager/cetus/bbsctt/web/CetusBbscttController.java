package kware.apps.manager.cetus.bbsctt.web;

import cetus.user.UserUtil;
import kware.apps.manager.cetus.answer.dto.response.AnswerList;
import kware.apps.manager.cetus.answer.service.CetusBbscttAnswerService;
import kware.apps.system.bbs.domain.CetusBbs;
import kware.apps.system.bbs.service.CetusBbsService;
import kware.apps.manager.cetus.bbsctt.domain.CetusBbsctt;
import kware.apps.manager.cetus.bbsctt.dto.request.BbscttTpSearch;
import kware.apps.manager.cetus.bbsctt.dto.response.BbscttView;
import kware.apps.manager.cetus.bbsctt.service.CetusBbscttService;
import kware.apps.manager.cetus.enumstatus.BbsTpCd;
import kware.common.config.auth.MenuNavigationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/portal/bbs")
public class CetusBbscttController {

    private final CetusBbscttService bbscttService;
    private final CetusBbsService bbsService;
    private final CetusBbscttAnswerService answerService;
    private final MenuNavigationManager menuNavigationManager;

    /*@GetMapping("/{bbsUid}")
    public String index(@PathVariable("bbsUid") Long bbsUid, final Model model) {
        CetusBbs bbs = bbsService.view(bbsUid);
        model.addAttribute("bbs", bbs);
        menuNavigationManager.renderingPage("/portal/cetus/bbsctt/"+bbsUid, bbs.getBbsNm(), true, model);
        return "asp/bbsctt/index";
    }*/

    @GetMapping("/{bbsTpCd}")
    public String index(@PathVariable("bbsTpCd") String bbsTpCd, final Model model) {
        CetusBbs bbs = bbsService.getBbsByTpCd(new BbscttTpSearch(BbsTpCd.fromSubcode(bbsTpCd).name(), UserUtil.getUserWorkplaceUid()));
        model.addAttribute("bbsUid", bbs.getBbsUid());
        model.addAttribute("bbs", bbs);
        menuNavigationManager.renderingPage("/portal/bbs/"+bbsTpCd, bbs.getBbsNm(), true, model);
        return "asp/bbsctt/index";
    }

    /*@GetMapping("/save/{bbsUid}")
    public String save(@PathVariable("bbsUid") Long bbsUid, final Model model) {
        CetusBbs bbs = bbsService.view(bbsUid);
        model.addAttribute("bbs", bbs);
        menuNavigationManager.renderingPage("/portal/bbs/"+bbsTpCd, "게시글 등록", false, model);
        return "asp/bbsctt/save";
    }*/

    @GetMapping("/{bbsTpCd}/save")
    public String save(@PathVariable("bbsTpCd") String bbsTpCd, final Model model) {
        CetusBbs bbs = bbsService.getBbsByTpCd(new BbscttTpSearch(BbsTpCd.fromSubcode(bbsTpCd).name(), UserUtil.getUserWorkplaceUid()));
        model.addAttribute("bbsUid", bbs.getBbsUid());
        model.addAttribute("bbs", bbs);
        menuNavigationManager.renderingPage("/portal/bbs/"+bbsTpCd, "게시글 등록", false, model);
        return "asp/bbsctt/save";
    }

    @GetMapping("/{bbsTpCd}/{bbscttUid}")
    public String view( @PathVariable("bbsTpCd") String bbsTpCd,
                        @PathVariable("bbscttUid") Long bbscttUid,
                        final Model model, HttpServletRequest req, HttpServletResponse res) {
        BbscttView bbsctt = bbscttService.findViewByBbscttUid(bbscttUid);
        model.addAttribute("bbsctt", bbsctt);

        CetusBbs bbs = bbsService.view(bbsctt.getBbsUid());
        model.addAttribute("bbs", bbs);
        bbscttService.increaseViewCount(bbscttUid, req, res);

        List<AnswerList> answers = answerService.findAllAnswerList(bbscttUid);
        model.addAttribute("answers", answers);

        menuNavigationManager.renderingPage("/portal/bbs/"+bbsTpCd, "게시글 조회", false, model);

        return "asp/bbsctt/view";
    }

    @GetMapping("/{bbsTpCd}/form/{bbscttUid}")
    public String form( @PathVariable("bbsTpCd") String bbsTpCd,
                        @PathVariable("bbscttUid") Long bbscttUid,
                        final Model model) {
        CetusBbsctt bbsctt = bbscttService.view(bbscttUid);
        model.addAttribute("bbsctt", bbsctt);

        CetusBbs bbs = bbsService.view(bbsctt.getBbsUid());
        model.addAttribute("bbs", bbs);

        menuNavigationManager.renderingPage("/portal/bbs/"+bbsTpCd, "게시글 수정", false, model);
        return "asp/bbsctt/form";
    }
}
