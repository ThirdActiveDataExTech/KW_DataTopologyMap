package kware.apps.mobigen.cetus.dataset.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.mobigen.cetus.dataset.dto.request.SearchMobigenDataset;
import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/mobigen-dataset")
public class CetusMobigenDatasetRestController {

    private final CetusMobigenDatasetService service;

    @GetMapping
    public ResponseEntity findAllMobigenDatasetPage(SearchMobigenDataset search, Pageable pageable) {
        Page<MobigenDatasetList> page = service.findAllMobigenDatasetPage(search, pageable);
        return ResponseEntity.ok(page);
    }
}
