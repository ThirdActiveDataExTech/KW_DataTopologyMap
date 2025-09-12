package kware.apps.thirdeye.dataset.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.thirdeye.dataset.dto.request.DatasetSearch;
import kware.apps.thirdeye.dataset.dto.response.DatasetList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
