package kware.apps.thirdeye.approveddataset.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.thirdeye.approveddataset.dto.request.ApprovedDatasetSearch;
import kware.apps.thirdeye.approveddataset.dto.response.DatasetList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portal/approved-dataset")
public class CetusApprovedDatasetRestController {

    private final CetusApprovedDatasetService service;

    @GetMapping("/list")
    public ResponseEntity findDatasetList(ApprovedDatasetSearch search) {
        List<DatasetList> datasetList = service.findDatasetList(search);
        return ResponseEntity.ok(datasetList);
    }
}
