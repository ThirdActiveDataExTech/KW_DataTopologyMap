package kware.apps.thirdeye.mobigen.approveddataset.web;


import kware.apps.thirdeye.mobigen.approveddataset.domain.ApprovedDatasetTargetTpCd;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.ApprovedDatasetView;
import kware.apps.thirdeye.mobigen.approveddataset.service.CetusApprovedDatasetService;
import kware.apps.thirdeye.mobigen.mainui.domain.DatasetMainUiType;
import kware.apps.thirdeye.mobigen.mainui.dto.response.MainUiList;
import kware.apps.thirdeye.mobigen.mainui.service.CetusDatasetMainUiService;
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
@RequestMapping("/admin/approved/dataset")
public class CetusApprovedDatasetController {

    private final CetusApprovedDatasetService service;
    private final CetusDatasetMainUiService mainUiService;
    private final MenuNavigationManager menuNavigationManager;

    @GetMapping
    public String index(Model model) {
        menuNavigationManager.renderingPage("/admin/approved/dataset", "데이터 진열 관리", true, model);
        model.addAttribute("uiType", DatasetMainUiType.toMap());
        model.addAttribute("targetType", ApprovedDatasetTargetTpCd.toMap());
        return "admin/approved/index";
    }

    @GetMapping("/save")
    public String save(Model model) {
        menuNavigationManager.renderingPage("/admin/approved/dataset", "데이터 진열 관리 등록", false, model);

        // 화면 UI 유형
        List<MainUiList> mainUiList = mainUiService.findDatasetMainUiList();
        model.addAttribute("mainUiList", mainUiList);

        return "admin/approved/save";
    }

    @GetMapping("/form/{approvedUid}")
    public String form(@PathVariable("approvedUid") Long approvedUid, Model model) {
        menuNavigationManager.renderingPage("/admin/approved/dataset", "데이터 진열 관리 수정", false, model);

        // 1. 화면 UI 유형
        List<MainUiList> mainUiList = mainUiService.findDatasetMainUiList();
        model.addAttribute("mainUiList", mainUiList);

        // 2. 진열등록/관리 중인 데이터셋 정보
        // => 데이터셋의 상세 정보 (by. 모비젠)
        // => 데이터셋의 화면 UI 정보
        ApprovedDatasetView approvedDatasetView = service.findApprovedDatasetView(approvedUid);
        model.addAttribute("view", approvedDatasetView);

        return "admin/approved/form";
    }
}
