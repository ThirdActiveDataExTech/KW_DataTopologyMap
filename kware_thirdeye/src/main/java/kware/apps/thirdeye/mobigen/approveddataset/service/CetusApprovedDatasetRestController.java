package kware.apps.thirdeye.mobigen.approveddataset.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.HomeDatasetSearch;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.SearchApprovedDataset;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.ApprovedDatasetItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portal/approved-dataset")
public class CetusApprovedDatasetRestController {

    private final CetusApprovedDatasetService service;

    /**
     *
     * kware 포탈 시스템에서 진열등록/관리 중인 데이터셋 목록 조회
     *
     * @api         [GET] /api/portal/approved-dataset/page
     * @author      dahyeon
     * @date        2025-10-15
    **/
    @GetMapping("/page")
    public ResponseEntity findDatasetList(SearchApprovedDataset search, Pageable pageable) {
        Page<ApprovedDatasetItem> datasetPage = service.findDatasetPage(search, pageable);
        return ResponseEntity.ok(datasetPage);
    }

    /**
     *
     * 메인 홈화면의 데이터셋 목록을 뿌려주기 위한 API
     * => 같은 {화면 UI} 값을 갖는 데이터셋 목록이 조회된다.
     * => 만일 선택된 카테고리가 있다면, 해당 카테고리 정보를 갖는 데이터셋 목록만 조회된다.
     *
     * @api         [GET] /api/portal/approved-dataset/home
     * @author      dahyeon
     * @date        2025-10-15
    **/
    @GetMapping("/home")
    public ResponseEntity findHomeDatasetList(HomeDatasetSearch search) {
        List<ApprovedDatasetItem> list = service.findHomeDatasetList(search);
        return ResponseEntity.ok(list);
    }
}
