package kware.apps.thirdeye.approveddataset.web;


import ch.qos.logback.core.joran.event.SaxEventRecorder;
import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetView;
import kware.apps.mobigen.cetus.dataset.service.CetusMobigenDatasetService;
import kware.apps.thirdeye.approveddataset.dto.response.ApprovedDatasetView;
import kware.apps.thirdeye.approveddataset.dto.response.DatasetIdList;
import kware.apps.thirdeye.approveddataset.service.CetusDatasetService;
import kware.apps.thirdeye.mainui.dto.response.MainUiList;
import kware.apps.thirdeye.mainui.service.CetusDatasetMainUiService;
import kware.common.config.auth.menu.MenuNavigationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/approved/dataset")
public class CetusApprovedDatasetController {

    private final CetusDatasetService service;
    private final CetusDatasetMainUiService mainUiService;
    private final CetusMobigenDatasetService mobigenDatasetService;
    private final MenuNavigationManager menuNavigationManager;

    @GetMapping
    public String index(Model model) {
        menuNavigationManager.renderingPage("/admin/approved/dataset", "데이터 진열 관리", true, model);
        return "admin/approved/index";
    }

    @GetMapping("/save")
    public String save(Model model) {
        menuNavigationManager.renderingPage("/admin/approved/dataset", "데이터 진열 관리 등록", false, model);

        List<MainUiList> mainUiList = mainUiService.findDatasetMainUiList();
        model.addAttribute("mainUiList", mainUiList);

        List<Long> ids = service.findApprovedDatasetIdList().stream().map(DatasetIdList::getDatasetId).collect(Collectors.toList());
        model.addAttribute("approvedIds", ids);

        return "admin/approved/save";
    }

    @GetMapping("/form/{approvedUid}")
    public String form(@PathVariable("approvedUid") Long approvedUid, Model model) {
        menuNavigationManager.renderingPage("/admin/approved/dataset", "데이터 진열 관리 수정", false, model);

        List<MainUiList> mainUiList = mainUiService.findDatasetMainUiList();
        model.addAttribute("mainUiList", mainUiList);

        ApprovedDatasetView approvedDatasetView = service.findApprovedDatasetView(approvedUid);
        model.addAttribute("view", approvedDatasetView);

        return "admin/approved/form";
    }
}
