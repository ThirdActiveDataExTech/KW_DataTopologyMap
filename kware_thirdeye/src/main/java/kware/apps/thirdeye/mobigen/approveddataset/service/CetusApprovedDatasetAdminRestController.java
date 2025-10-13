package kware.apps.thirdeye.mobigen.approveddataset.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.ApprovedDatasetSearch;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.DeleteApprovedDatasets;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.SaveApprovedDataset;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.DatasetList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/approved-dataset")
public class CetusApprovedDatasetAdminRestController {

    private final CetusApprovedDatasetService service;

    @GetMapping("/page")
    public ResponseEntity findDatasetPage(ApprovedDatasetSearch search, Pageable pageable) {
        Page<DatasetList> datasetPage = service.findDatasetPage(search, pageable);
        return ResponseEntity.ok(datasetPage);
    }

    @PostMapping("/approve")
    public ResponseEntity approveDataset(@RequestBody SaveApprovedDataset request) {
        service.approveDataset(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/delete-several")
    public ResponseEntity deleteSeveralApprovedDataset(@RequestBody DeleteApprovedDatasets request) {
        service.deleteSeveralApprovedDataset(request);
        return ResponseEntity.ok().build();
    }
}
