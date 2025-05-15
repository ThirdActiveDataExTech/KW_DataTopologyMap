package kware.apps.manager.cetus.bbsctt.web;

import kware.apps.manager.cetus.bbs.domain.CetusBbs;
import kware.apps.manager.cetus.bbs.service.CetusBbsService;
import kware.apps.manager.cetus.bbsctt.domain.CetusBbsctt;
import kware.apps.manager.cetus.bbsctt.dto.response.BbscttView;
import kware.apps.manager.cetus.bbsctt.service.CetusBbscttService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/asp/cetus/bbsctt")
public class CetusBbscttController {

    private final CetusBbscttService bbscttService;
    private final CetusBbsService bbsService;

    @GetMapping("/{bbsUid}")
    public String index(@PathVariable("bbsUid") Long bbsUid, Model model) {

        CetusBbs view = bbsService.view(bbsUid);
        model.addAttribute("title1", view.getBbsNm());
        model.addAttribute("title2", view.getBbsNm());
        model.addAttribute("bbsNm", view.getBbsNm());

        return "asp/bbsctt/index";
    }

    @GetMapping("/save/{bbsUid}")
    public String save(@PathVariable("bbsUid") Long bbsUid, Model model) {

        CetusBbs view = bbsService.view(bbsUid);
        model.addAttribute("title1", view.getBbsNm());
        model.addAttribute("title2", view.getBbsNm());
        model.addAttribute("bbsNm", view.getBbsNm());
        model.addAttribute("view", view);
        return "asp/bbsctt/save";
    }

    @GetMapping("/view/{bbscttUid}")
    public String view(@PathVariable("bbscttUid") Long bbscttUid, Model model,
                       HttpServletRequest req,
                       HttpServletResponse res) {

        BbscttView bbsctt = bbscttService.findViewByBbscttUid(bbscttUid);
        model.addAttribute("bbsctt", bbsctt);

        CetusBbs bbs = bbsService.view(bbsctt.getBbsUid());
        model.addAttribute("bbs", bbs);
        model.addAttribute("title1", bbs.getBbsNm());
        model.addAttribute("title2", bbs.getBbsNm());
        model.addAttribute("bbsNm", bbs.getBbsNm());

        bbscttService.increaseViewCount(bbscttUid, req, res);

        return "asp/bbsctt/view";
    }

    @GetMapping("/form/{bbscttUid}")
    public String form(@PathVariable("bbscttUid") Long bbscttUid, Model model,
                       HttpServletRequest req,
                       HttpServletResponse res) {

        CetusBbsctt bbsctt = bbscttService.view(bbscttUid);
        model.addAttribute("bbsctt", bbsctt);

        CetusBbs bbs = bbsService.view(bbsctt.getBbsUid());
        model.addAttribute("bbs", bbs);
        model.addAttribute("title1", bbs.getBbsNm());
        model.addAttribute("title2", bbs.getBbsNm());
        model.addAttribute("bbsNm", bbs.getBbsNm());

        return "asp/bbsctt/form";
    }
}
