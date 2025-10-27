package kware.apps.thirdeye;


import cetus.user.UserUtil;
import kware.apps.system.user.dto.response.UserFullInfo;
import kware.apps.system.user.service.CetusUserService;
import kware.apps.thirdeye.bbsctt.service.CetusBbscttService;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.ApprovedDatasetView;
import kware.apps.thirdeye.mobigen.approveddataset.service.CetusApprovedDatasetService;
import kware.common.config.auth.dto.SessionUserInfo;
import kware.common.config.auth.menu.MenuNavigationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/portal")
@RequiredArgsConstructor
public class PortalController {

    private final CetusUserService cetusUserService;
    private final MenuNavigationManager menuNavigationManager;
    private final CetusApprovedDatasetService datasetService;
    private final CetusBbscttService bbscttService;

    @GetMapping("/home")
    public String home(Model model) {
        menuNavigationManager.renderingPage("/portal/home", "HOME", true, model);
        return "thirdeye/dataset/home";
    }

    @GetMapping("/list")
    public String list(Model model) {
        menuNavigationManager.renderingPage("/portal/list", "LIST", true, model);
        return "thirdeye/dataset/list";
    }

    @GetMapping("/detail/{approvedUid}")
    public String openDetail(@PathVariable("approvedUid") Long approvedUid, Model model) {
        menuNavigationManager.renderingPage("/portal/list", "Detail", false, model);

        ApprovedDatasetView dataset = datasetService.findApprovedDatasetView(approvedUid);
        model.addAttribute("dataset", dataset);

        if(dataset == null) return "error/404";
        if("Y".equals(dataset.getDeleteAt())) return "error/error";

        return "thirdeye/dataset/detail";
    }

    @GetMapping("/myInfo")
    public String myInfo(Model model) {
        SessionUserInfo user = UserUtil.getUser();
        UserFullInfo info = cetusUserService.findUserFullInfoByUserUid(user.getUid());
        model.addAttribute("view", info);

        /*model.addAttribute("fields", columnsService.getFormGroupColumns("SIGNUP"));
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
        model.addAttribute("metadata", map);*/

        menuNavigationManager.renderingPage("/portal/myInfo", "내 정보 수정", true, model);

        return "thirdeye/myInfo/index";
    }

    @GetMapping("/chart")
    public String chart(Model model) {
        menuNavigationManager.renderingPage("/portal/chart", "관계데이터 다차원 탐색", true, model);
        return "thirdeye/dataset/chart";
    }
}
