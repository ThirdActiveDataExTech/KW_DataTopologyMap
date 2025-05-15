package kware.apps.manager.cetus.user.web;


import kware.apps.manager.cetus.bbs.service.CetusBbsService;
import kware.apps.manager.cetus.enumstatus.UserAuthorCd;
import kware.apps.manager.cetus.enumstatus.UserStatus;
import kware.apps.manager.cetus.group.service.CetusGroupService;
import kware.apps.manager.cetus.position.service.CetusPositionService;
import kware.apps.manager.cetus.user.dto.response.UserFullInfo;
import kware.apps.manager.cetus.user.dto.response.UserView;
import kware.apps.manager.cetus.user.service.CetusUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manager/cetus/user")
public class CetusUserController {

    private final CetusUserService service;
    private final CetusGroupService groupService;
    private final CetusPositionService positionService;
    private final CetusBbsService bbsService;


    @GetMapping({"", "/"})
    public String index(Model model) {

        model.addAttribute("userAuthorCd", UserAuthorCd.toList());
        model.addAttribute("userStatus", UserStatus.toList());
        model.addAttribute("userGroup", groupService.findGroupList());
        model.addAttribute("userPosition", positionService.findPositionList());

        return "manager/user/index";
    }

    @GetMapping("/{uid}")
    public String form(@PathVariable("uid") Long uid, Model model) {
        UserFullInfo info = service.findUserFullInfoByUserUid(uid);
        model.addAttribute("form", info);

        model.addAttribute("userAuthorCd", UserAuthorCd.toList());
        model.addAttribute("userStatus", UserStatus.toList());
        model.addAttribute("userGroup", groupService.findGroupList());
        model.addAttribute("userPosition", positionService.findPositionList());

        model.addAttribute("bbsList", bbsService.findAllWorkplaceBbs());

        return "manager/user/form";
    }

    @GetMapping("/save")
    public String save(Model model) {
        model.addAttribute("isAdminJoin", true);
        model.addAttribute("userAuthorCd", UserAuthorCd.toList());
        model.addAttribute("userGroup", groupService.findGroupList());
        model.addAttribute("userPosition", positionService.findPositionList());
        return "manager/user/save";
    }
}
