package kware.apps.thirdeye.mobigen.datasetui.service;


import kware.apps.thirdeye.mobigen.datasetui.dto.request.ChangeDatasetUi;
import kware.apps.thirdeye.mobigen.datasetui.dto.request.ChangeShowUiDatasets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/dataset-ui")
public class CetusDatasetUiAdminRestController {

    private final CetusDatasetUiService service;

    /**
     *
     * KWARE 포탈 시스템에서 진열등록/관리 중인 데이터셋에 대한 화면 UI 정보 수정
     *
     * @api         [PUT] /api/admin/dataset-ui/{uid}
     * @author      dahyeon
     * @date        2025-10-15
    **/
    @PutMapping("/{uid}")
    public ResponseEntity changeApprovedDataset(@PathVariable("uid") Long uid, @RequestBody ChangeDatasetUi request) {
        service.changeApprovedDatasetUi(uid, request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * KWARE 포탈 시스템에서 진열등록/관리 중인 데이터셋 공개 여부 > 공개로 수정
     *
     * @api         [PUT] /api/admin/dataset-ui/showY-several
     * @author      dahyeon
     * @date        2025-10-17
    **/
    @PutMapping("/showY-several")
    public ResponseEntity showYSeveralApprovedDataset(@RequestBody ChangeShowUiDatasets request) {
        service.changeShowApprovedDataset(request, "Y");
        return ResponseEntity.ok().build();
    }

    /**
     *
     * KWARE 포탈 시스템에서 진열등록/관리 중인 데이터셋 공개 여부 > 공개로 수정
     *
     * @api         [PUT] /api/admin/dataset-ui/showN-several
     * @author      dahyeon
     * @date        2025-10-17
    **/
    @PutMapping("/showN-several")
    public ResponseEntity showNSeveralApprovedDataset(@RequestBody ChangeShowUiDatasets request) {
        service.changeShowApprovedDataset(request, "N");
        return ResponseEntity.ok().build();
    }
}
