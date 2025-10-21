package kware.apps.mobigen.cetus.dataset.service;


import cetus.bean.Page;
import kware.apps.mobigen.cetus.dataset.dto.request.*;
import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetList;
import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetRealDataList;
import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetRealDataView;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Delete;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/mobigen-dataset")
public class CetusMobigenDatasetRestController {

    private final CetusMobigenDatasetService service;

    /**
     *
     * 모비젠 데이터셋 목록 페이징 조회
     *
     * @api         [GET] /api/admin/mobigen-dataset
     * @author      dahyeon
     * @date        2025-10-15
    **/
    @GetMapping
    public ResponseEntity findAllMobigenDatasetPage(SearchMobigenDataset search) {
        Page<MobigenDatasetList> page = service.findAllMobigenDatasetPage(search);
        return ResponseEntity.ok(page);
    }

    /**
     *
     * 모비젠 데이터셋 리스트 조회
     *
     * @api         [GET] /api/admin/mobigen-dataset/list
     * @author      dahyeon
     * @date        2025-10-15
    **/
    @GetMapping("/list")
    public ResponseEntity findAllMobigenDatasetList(SearchMobigenDataset search) {
        Page<MobigenDatasetList> page = service.findAllMobigenDatasetList(search);
        return ResponseEntity.ok(page);
    }

    /**
     *
     * 모비젠 측에 데이터셋 정보 등록(저장)
     *
     * @api         [POST] /api/admin/mobigen-dataset
     * @author      dahyeon
     * @date        2025-10-15
    **/
    @PostMapping
    public ResponseEntity saveMobigenDataset(@RequestBody SaveMobigenDataset request) {
        service.saveMobigenDataset(request);
        return ResponseEntity.ok().build();
    }


    /**
     *
     * 모비젠 측에 데이터셋 정보 등록(저장)
     * => 패키지 파일 형태로 업로드 (zip)
     *
     * @api         [POST] /api/admin/mobigen-dataset/package
     * @author      dahyeon
     * @date        2025-10-17
    **/
    @PostMapping("/package")
    public ResponseEntity saveMobigenPackageDataset(@RequestBody SaveMobigenPackageDataset request) {
        service.saveMobigenPackageDataset(request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 모비젠 측의 데이터셋 정보 삭제
     * => 추후 모비젠 측 연결시, [하드 삭제]로 동작된다.
     * => KWARE 포탈 시스템 구성은 [논리 삭제]로 동작중
     *
     * @api         [PUT] /api/admin/mobigen-dataset/delete-several
     * @author      dahyeon
     * @date        2025-10-15
    **/
    @PutMapping("/delete-several")
    public ResponseEntity deleteSeveralMobigenDataset(@RequestBody DeleteDatasets request) {
        service.deleteSeveralMobigenDataset(request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 모비젠 측의 데이터셋 정보 수정
     * (1) 데이터 정보 수정 가능
     * (2) 원본(실)데이터 파일 추가 업로드 가능 ( 여러개의 원본데이터가 있다면, 주기성 데이터셋처럼 보이게 된다. )
     *
     * @api         [PUT] /api/admin/mobigen-dataset/{datasetId}
     * @author      dahyeon
     * @date        2025-10-15
    **/
    @PutMapping("/{datasetId}")
    public ResponseEntity changeMobigenDataset(@PathVariable("datasetId") Long datasetId, @RequestBody ChangeMobigenDataset request) {
        service.changeMobigenDataset(datasetId, request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 모비젠 데이터셋에 대해서 원본(실)데이터 파일 정보 조회
     *
     * @api         [GET] /api/admin/mobigen-dataset/realdata/{fileId}
     * @author      dahyeon
     * @date        2025-10-15
    **/
    @GetMapping("/realdata/{fileId}")
    public ResponseEntity findRealDataInfoByFileId(@PathVariable("fileId") String fileId) {
        MobigenDatasetRealDataView view = service.findRealDataInfoByFileId(fileId);
        return ResponseEntity.ok(view);
    }

    /**
     *
     * 메타데이터 하위 원본데이터파일 목록 페이징 조회
     *
     * @api         [GET] /api/admin/mobigen-dataset/realdata/page/{datasetId}
     * @author      dahyeon
     * @date        2025-10-21
    **/
    @GetMapping("/realdata/page/{datasetId}")
    public ResponseEntity findRealDataPage(@PathVariable("datasetId") Long datasetId, SearchMobigenDatasetRealdata search) {
        Page<MobigenDatasetRealDataList> page =  service.findRealDataPage(datasetId, search);
        return ResponseEntity.ok(page);
    }

    /**
     *
     * 메타데이터 하위 원본데이너파일 여러건 삭제
     *
     * @api         [PUT] /api/admin/mobigen-dataset/delete-realdatas
     * @author      dahyeon
     * @date        2025-10-21
    **/
    @PutMapping("/delete-realdatas")
    public ResponseEntity deleteRealdatas(@RequestBody DeleteRealdatas request) {
        service.deleteRealdatas(request);
        return ResponseEntity.ok().build();
    }
}
