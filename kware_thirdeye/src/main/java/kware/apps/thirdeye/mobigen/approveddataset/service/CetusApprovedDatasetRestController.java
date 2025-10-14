package kware.apps.thirdeye.mobigen.approveddataset.service;


import kware.apps.thirdeye.mobigen.approveddataset.dto.request.ApprovedDatasetSearch;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.HomeDatasetSearch;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.DatasetList;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.HomeDatasetList;
import lombok.Getter;
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

    @GetMapping("/home")
    public ResponseEntity findHomeDatasetList(HomeDatasetSearch search) {
        List<HomeDatasetList> list = service.findHomeDatasetList(search);
        return ResponseEntity.ok(list);
    }
}
