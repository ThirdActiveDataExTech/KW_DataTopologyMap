package kware.apps.thirdeye.mobigen.datasetui.service;

import kware.apps.thirdeye.mobigen.datasetui.dto.response.DatasetUiGroup;
import kware.apps.thirdeye.mobigen.datasetui.dto.response.DatasetUiList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portal/dataset-ui")
public class CetusDatasetUiRestController {

    private final CetusDatasetUiService service;

    /**
     *
     * 데이터셋 UI로 사용중인 MAIN_UI 정보를 그룹핑해서 조회
     * => 메인 홈 화면을 구성하기 위한 API
     *
     * @api         [GET] /api/portal/dataset-ui/grouping
     * @author      dahyeon
     * @date        2025-10-15
    **/
    @GetMapping("/grouping")
    public ResponseEntity findDatasetUiByGroup() {
        List<DatasetUiGroup> list = service.findDatasetUiByGroup();
        return ResponseEntity.ok(list);
    }

}
