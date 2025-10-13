package kware.apps.mobigen.cetus.dataset.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.mobigen.cetus.dataset.dto.request.ChangeMobigenDataset;
import kware.apps.mobigen.cetus.dataset.dto.request.DeleteDatasets;
import kware.apps.mobigen.cetus.dataset.dto.request.SaveMobigenDataset;
import kware.apps.mobigen.cetus.dataset.dto.request.SearchMobigenDataset;
import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetList;
import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetRealDataView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity saveMobigenDataset(@RequestBody SaveMobigenDataset request) {
        service.saveMobigenDataset(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/delete-several")
    public ResponseEntity deleteSeveralMobigenDataset(@RequestBody DeleteDatasets request) {
        service.deleteSeveralMobigenDataset(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{uid}")
    public ResponseEntity changeMobigenDataset(@PathVariable("uid") Long uid, @RequestBody ChangeMobigenDataset request) {
        service.changeMobigenDataset(uid, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/realdata/{fileId}")
    public ResponseEntity findRealDataInfoByFileId(@PathVariable("fileId") String fileId) {
        MobigenDatasetRealDataView view = service.findRealDataInfoByFileId(fileId);
        return ResponseEntity.ok(view);
    }
}
