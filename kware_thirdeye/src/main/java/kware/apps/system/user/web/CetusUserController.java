package kware.apps.system.user.web;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kware.apps.thirdeye.enumstatus.UserAuthorCd;
import kware.apps.thirdeye.enumstatus.UserStatus;
import kware.apps.system.form.service.CetusFormColumnsService;
import kware.apps.system.group.service.CetusGroupService;
import kware.apps.system.bbs.service.CetusBbsService;
import kware.apps.system.position.service.CetusPositionService;
import kware.apps.system.user.dto.response.UserFullInfo;
import kware.apps.system.user.service.CetusUserService;
import kware.common.config.auth.MenuNavigationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/system/user")
public class CetusUserController {

    private final CetusUserService service;
    private final CetusGroupService groupService;
    private final CetusPositionService positionService;
    private final CetusBbsService bbsService;
    private final CetusFormColumnsService columnsService;
    private final MenuNavigationManager menuNavigationManager;

    @GetMapping
    public String index(Model model) {

        model.addAttribute("userAuthorCd", UserAuthorCd.toList());
        model.addAttribute("userStatus", UserStatus.toList());
        model.addAttribute("userGroup", groupService.findGroupList());
        model.addAttribute("userPosition", positionService.findPositionList());
        menuNavigationManager.renderingPage("/system/user", "사용자 관리", true, model);

        return "system/user/index";
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

        menuNavigationManager.renderingPage("/system/user", "사용자 상세 정보", false, model);

        return "system/user/form";
    }

    @GetMapping("/save")
    public String save(Model model) {
        model.addAttribute("userAuthorCd", UserAuthorCd.toList());
        model.addAttribute("userGroup", groupService.findGroupList());
        model.addAttribute("userPosition", positionService.findPositionList());
        model.addAttribute("fields", columnsService.getFormGroupColumns("SIGNUP"));
        menuNavigationManager.renderingPage("/system/user", "계정 추가", false, model);
        return "system/user/save";
    }

}
