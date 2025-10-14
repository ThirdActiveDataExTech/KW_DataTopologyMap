package kware.apps.thirdeye.mobigen.datasetui.service;


import kware.apps.thirdeye.mobigen.datasetui.dto.request.ChangeDatasetUi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/dataset-ui")
public class CetusDatasetUiAdminRestController {

    private final CetusDatasetUiService service;

    @PutMapping("/{uid}")
    public ResponseEntity changeApprovedDataset(@PathVariable("uid") Long uid, @RequestBody ChangeDatasetUi request) {
        service.changeApprovedDatasetUi(uid, request);
        return ResponseEntity.ok().build();
    }
}
