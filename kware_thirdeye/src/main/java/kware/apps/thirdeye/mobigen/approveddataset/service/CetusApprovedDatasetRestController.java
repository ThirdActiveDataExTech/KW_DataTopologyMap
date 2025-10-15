package kware.apps.thirdeye.mobigen.approveddataset.service;


import kware.apps.thirdeye.mobigen.approveddataset.dto.request.ApprovedDatasetSearch;
import kware.apps.thirdeye.mobigen.approveddataset.dto.request.HomeDatasetSearch;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.ApprovedDatasetList;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.HomeDatasetList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * @api         [GET] /api/portal/approved-dataset/list
     * @author      dahyeon
     * @date        2025-10-15
    **/
    @GetMapping("/list")
    public ResponseEntity findDatasetList(ApprovedDatasetSearch search) {
        List<ApprovedDatasetList> datasetList = service.findDatasetList(search);
        return ResponseEntity.ok(datasetList);
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
        List<HomeDatasetList> list = service.findHomeDatasetList(search);
        return ResponseEntity.ok(list);
    }
}
