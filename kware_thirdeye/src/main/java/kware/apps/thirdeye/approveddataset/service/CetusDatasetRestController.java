package kware.apps.thirdeye.approveddataset.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.thirdeye.approveddataset.dto.request.DatasetSearch;
import kware.apps.thirdeye.approveddataset.dto.request.SaveDataset;
import kware.apps.thirdeye.approveddataset.dto.response.DatasetList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portal/dataset")
public class CetusDatasetRestController {

    private final CetusDatasetService service;

    @GetMapping("/page")
    public ResponseEntity findDatasetPage(DatasetSearch search, Pageable pageable) {
        Page<DatasetList> datasetPage = service.findDatasetPage(search, pageable);
        return ResponseEntity.ok(datasetPage);
    }
}
