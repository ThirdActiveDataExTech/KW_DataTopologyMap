package kware.apps.thirdeye.bbsctt.web;

import cetus.user.UserUtil;
import kware.apps.thirdeye.answer.dto.response.AnswerList;
import kware.apps.thirdeye.answer.service.CetusBbscttAnswerService;
import kware.apps.system.bbs.domain.CetusBbs;
import kware.apps.system.bbs.service.CetusBbsService;
import kware.apps.thirdeye.bbsctt.domain.CetusBbsctt;
import kware.apps.thirdeye.bbsctt.dto.request.BbscttTpSearch;
import kware.apps.thirdeye.bbsctt.dto.response.BbscttView;
import kware.apps.thirdeye.bbsctt.service.CetusBbscttService;
import kware.apps.thirdeye.enumstatus.BbsTpCd;
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

    @GetMapping("/{bbsTpCd}")
    public String index(@PathVariable("bbsTpCd") String bbsTpCd, final Model model) {
        CetusBbs bbs = bbsService.findBbsByTpCd(new BbscttTpSearch(BbsTpCd.fromSubcode(bbsTpCd).name(), UserUtil.getUserWorkplaceUid()));
        model.addAttribute("bbsUid", bbs.getBbsUid());
        model.addAttribute("bbs", bbs);
        menuNavigationManager.renderingPage("/portal/bbs/"+bbsTpCd, bbs.getBbsNm(), true, model);
        return "thirdeye/bbsctt/index";
    }

    @GetMapping("/{bbsTpCd}/save")
    public String save(@PathVariable("bbsTpCd") String bbsTpCd, final Model model) {
        CetusBbs bbs = bbsService.findBbsByTpCd(new BbscttTpSearch(BbsTpCd.fromSubcode(bbsTpCd).name(), UserUtil.getUserWorkplaceUid()));
        model.addAttribute("bbsUid", bbs.getBbsUid());
        model.addAttribute("bbs", bbs);
        menuNavigationManager.renderingPage("/portal/bbs/"+bbsTpCd, "게시글 등록", false, model);
        return "thirdeye/bbsctt/save";
    }

    @GetMapping("/{bbsTpCd}/{bbscttUid}")
    public String view( @PathVariable("bbsTpCd") String bbsTpCd,
                        @PathVariable("bbscttUid") Long bbscttUid,
                        final Model model, HttpServletRequest req, HttpServletResponse res ) {
        BbscttView bbsctt = bbscttService.findViewByBbscttUid(bbscttUid);
        model.addAttribute("bbsctt", bbsctt);

        CetusBbs bbs = bbsService.view(bbsctt.getBbsUid());
        model.addAttribute("bbs", bbs);
        bbscttService.increaseViewCount(bbscttUid, req, res);

        List<AnswerList> answers = answerService.findAllAnswerList(bbscttUid);
        model.addAttribute("answers", answers);

        menuNavigationManager.renderingPage("/portal/bbs/"+bbsTpCd, "게시글 조회", false, model);

        return "thirdeye/bbsctt/view";
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
        return "thirdeye/bbsctt/form";
    }
}
