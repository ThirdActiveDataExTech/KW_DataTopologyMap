package kware.apps.system.bbs.web;


import cetus.user.UserUtil;
import kware.apps.system.bbs.dto.response.BbsTpCdCount;
import kware.apps.system.bbs.service.CetusBbsService;
import kware.common.config.auth.menu.MenuNavigationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/system/bbs")
public class CetusBbsController {

    private final CetusBbsService service;
    private final MenuNavigationManager menuNavigationManager;

    @GetMapping
    public String index(Model model) {
        menuNavigationManager.renderingPage("/system/bbs", "게시판 관리", true, model);
        return "system/bbs/index";
    }

    @GetMapping("/save")
    public String save(Model model) {
        List<BbsTpCdCount> bbsCounts = service.findBbsCountByBbsTpCd(UserUtil.getUserWorkplaceUid());
        menuNavigationManager.renderingPage("/system/bbs", "게시판 등록", false, model);
        model.addAttribute("bbsCounts", bbsCounts);
        return "system/bbs/save";
    }

    @GetMapping("/{uid}")
    public String form(@PathVariable("uid") Long uid, Model model) {
        menuNavigationManager.renderingPage("/system/bbs", "게시판 수정",false, model);
        model.addAttribute("form", service.findBbsByUid(uid));
        return "system/bbs/form";
    }
}
