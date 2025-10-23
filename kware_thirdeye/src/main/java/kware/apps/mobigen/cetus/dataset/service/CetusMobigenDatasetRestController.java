package kware.apps.mobigen.cetus.dataset.service;


import cetus.bean.Page;
import kware.apps.mobigen.cetus.dataset.dto.request.*;
import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetList;
import kware.apps.thirdeye.mobigen.datasetfile.dto.response.CetusDatasetFileView;
import lombok.RequiredArgsConstructor;
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
     * 메타데이터 하위 원본데이터파일 목록 페이징 조회
     *
     * @api         [GET] /api/admin/mobigen-dataset/realdata/page/{datasetId}
     * @author      dahyeon
     * @date        2025-10-21
    **/
    @GetMapping("/realdata/page/{datasetId}")
    public ResponseEntity findRealDataPage(@PathVariable("datasetId") Long datasetId, SearchMobigenDatasetRealdata search) {
        Page<CetusDatasetFileView> page =  service.findRealDataPage(datasetId, search);
        return ResponseEntity.ok(page);
    }

}
