package kware.apps.mobigen.cetus.dataset.web;


import kware.apps.mobigen.cetus.dataset.service.CetusMobigenDatasetService;
import kware.apps.mobigen.integration.dto.request.meta.SearchMetaValues;
import kware.apps.mobigen.integration.dto.request.metadata.SearchMetadataView;
import kware.apps.mobigen.integration.dto.response.meta.MetaKeyList;
import kware.apps.mobigen.integration.dto.response.meta.MetaKeyValueList;
import kware.apps.mobigen.integration.dto.response.metadata.MetadataView;
import kware.apps.mobigen.integration.service.DatasetService;
import kware.common.config.auth.menu.MenuNavigationManager;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/metadata/dataset")
public class CetusMobigenDatasetController {

    private final CetusMobigenDatasetService service;
    private final MenuNavigationManager menuNavigationManager;
    private final DatasetService datasetService;
    
    @GetMapping
    public String index(Model model) {
        MetaKeyList metaKeyList = datasetService.findMetaKeyList();
        Map<String, List<String>> mapKeyValues = new HashedMap();
        metaKeyList.getFilters().forEach(filterKey -> {
            MetaKeyValueList keyValueList = datasetService.findMetaKeyValueList(new SearchMetaValues(filterKey));
            mapKeyValues.put(filterKey, keyValueList.getValues());
        });
        menuNavigationManager.renderingPage("/admin/metadata/dataset", "메타 데이터 관리", true, model);
        model.addAttribute("mapKeyValues", mapKeyValues);
        return "admin/metadata/index";
    }

    @GetMapping("/save")
    public String save(Model model) {
        menuNavigationManager.renderingPage("/admin/metadata/dataset", "메타 데이터 등록", false, model);
        return "admin/metadata/mobigen/save";
    }

    @GetMapping("/package/save")
    public String packageSave(Model model) {
        menuNavigationManager.renderingPage("/admin/metadata/dataset", "패키지 데이터셋 등록", false, model);
        return "admin/metadata/mobigen/save-package";
    }

    @GetMapping("/{metadataId}")
    public String form(@PathVariable("metadataId") String metadataId, Model model) {
        menuNavigationManager.renderingPage("/admin/metadata/dataset", "메타 데이터 수정", false, model);
        MetadataView metadataView = datasetService.viewMetadata(new SearchMetadataView(metadataId));
        model.addAttribute("metadataView", metadataView);
        return "admin/metadata/mobigen/form";
    }

    @GetMapping("/realdata/{uid}")
    public String realdataForm(@PathVariable("uid") Long uid, Model model) {
        menuNavigationManager.renderingPage("/admin/metadata/dataset", "원본 데이터파일 수정", false, model);
        return "admin/metadata/mobigen/realdata-form";
    }
}
