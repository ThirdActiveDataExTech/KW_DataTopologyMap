package kware.apps.thirdeye.mobigen.approveddataset.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.SearchApprovedDataset;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.DeleteApprovedDatasets;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.SaveApprovedDataset;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.ApprovedDatasetItem;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.TitleBackfillResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/approved-dataset")
public class CetusApprovedDatasetAdminRestController {

    private final CetusApprovedDatasetService service;
    private final ApprovedDatasetTitleBackfillService titleBackfillService;

    /**
     *
     * kware 관리자 페이지에서 > 진열 관리 중인 데이터셋 목록 페이징 조회
     *
     * @api         [GET] /api/admin/approved-dataset/page
     * @author      dahyeon
     * @date        2025-10-15
    **/
    @GetMapping("/page")
    public ResponseEntity findDatasetPage(SearchApprovedDataset search, Pageable pageable) {
        Page<ApprovedDatasetItem> datasetPage = service.findDatasetPage(search, pageable);
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

    /**
     *
     * 진열 데이터셋 중 제목(extra_json.title)이 비어 있는 행에 모비젠 제목을 채운다(백필).
     * 모비젠이 연결돼 있지 않으면 건너뛰고 상태만 돌려준다. 복구 시 자동으로도 실행된다.
     *
     * @api         [POST] /api/admin/approved-dataset/backfill-title
    **/
    @PostMapping("/backfill-title")
    public ResponseEntity backfillTitle() {
        return ResponseEntity.ok(titleBackfillService.backfill());
    }

    /** 마지막 백필 실행 결과 */
    @GetMapping("/backfill-title")
    public ResponseEntity lastBackfillResult() {
        TitleBackfillResult result = titleBackfillService.getLastResult();
        return ResponseEntity.ok(result != null ? result : java.util.Collections.singletonMap("status", "NEVER_RUN"));
    }
}
