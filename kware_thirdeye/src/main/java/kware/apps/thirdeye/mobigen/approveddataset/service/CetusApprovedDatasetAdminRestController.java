package kware.apps.thirdeye.mobigen.approveddataset.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.ApprovedDatasetSearch;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.DeleteApprovedDatasets;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.SaveApprovedDataset;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.ApprovedDatasetList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/approved-dataset")
public class CetusApprovedDatasetAdminRestController {

    private final CetusApprovedDatasetService service;

    /**
     *
     * kware 관리자 페이지에서 > 진열 관리 중인 데이터셋 목록 페이징 조회
     *
     * @api         [GET] /api/admin/approved-dataset/page
     * @author      dahyeon
     * @date        2025-10-15
    **/
    @GetMapping("/page")
    public ResponseEntity findDatasetPage(ApprovedDatasetSearch search, Pageable pageable) {
        Page<ApprovedDatasetList> datasetPage = service.findDatasetPage(search, pageable);
        return ResponseEntity.ok(datasetPage);
    }

    /**
     *
     *  모비젠 측에 보관중인 데이터셋에 대해서 => kware 포탈 시스템으로 진열등록
     *  이때 해당 데이터셋에 대한 {화면 UI} 및 {기타 정보}들도 함께 등록+관리된다.
     *
     * @api         [POST] /api/admin/approved-dataset/approve
     * @author      dahyeon
     * @date        2025-10-15
    **/
    @PostMapping("/approve")
    public ResponseEntity approveDataset(@RequestBody SaveApprovedDataset request) {
        service.approveDataset(request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * KWARE 포탈 시스템에서 진열관리 중인 데이터셋에 대해 [논리 삭제] 진행
     *
     * @api         [PUT] /api/admin/approved-dataset/delete-several
     * @author      dahyeon
     * @date        2025-10-15
    **/
    @PutMapping("/delete-several")
    public ResponseEntity deleteSeveralApprovedDataset(@RequestBody DeleteApprovedDatasets request) {
        service.deleteSeveralApprovedDataset(request);
        return ResponseEntity.ok().build();
    }
}
