package kware.apps.thirdeye.approveddataset.service;


import kware.apps.thirdeye.approveddataset.dto.request.SaveDataset;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/dataset")
public class CetusAdminDatasetRestController {

    private final CetusDatasetService service;

    @PostMapping("/approve")
    public ResponseEntity approveDataset(@RequestBody SaveDataset request) {
        service.approveDataset(request);
        return ResponseEntity.ok().build();
    }
}
