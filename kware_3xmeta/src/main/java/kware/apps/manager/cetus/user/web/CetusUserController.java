package kware.apps.manager.cetus.user.web;


import kware.apps.manager.cetus.bbs.service.CetusBbsService;
import kware.apps.manager.cetus.enumstatus.UserAuthorCd;
import kware.apps.manager.cetus.enumstatus.UserStatus;
import kware.apps.manager.cetus.form.service.CetusFormColumnsService;
import kware.apps.manager.cetus.group.service.CetusGroupService;
import kware.apps.manager.cetus.position.service.CetusPositionService;
import kware.apps.manager.cetus.user.dto.response.UserFullInfo;
import kware.apps.manager.cetus.user.service.CetusUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manager/cetus/user")
public class CetusUserController {

    private final CetusUserService service;
    private final CetusGroupService groupService;
    private final CetusPositionService positionService;
    private final CetusBbsService bbsService;
    private final CetusFormColumnsService columnsService;


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
        model.addAttribute("fields", columnsService.getFormGroupColumns("SIGNUP"));
        Map<String, Object> formData = new HashMap<>();
        formData.put("companyAddress", "서울시 강남구");
        List<String> nations = new ArrayList<>();
        nations.add("south korea");
        nations.add("china");
        formData.put("nation", nations);
        formData.put("writeAt", "Y");
        formData.put("maximumAge", 19);
        formData.put("hobby", "등산");
        formData.put("birthday", "1995-08-15");
        model.addAttribute("metadata", formData);
        return "manager/user/form";
    }

    @GetMapping("/save")
    public String save(Model model) {
        model.addAttribute("isAdminJoin", true);
        model.addAttribute("isInvited", false);
        model.addAttribute("userAuthorCd", UserAuthorCd.toList());
        model.addAttribute("userGroup", groupService.findGroupList());
        model.addAttribute("userPosition", positionService.findPositionList());
        return "manager/user/save";
    }

}
