package kware.apps.thirdeye.mobigen.mainui.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.thirdeye.mobigen.mainui.dto.request.ChangeMainUi;
import kware.apps.thirdeye.mobigen.mainui.dto.request.SaveMainUi;
import kware.apps.thirdeye.mobigen.mainui.dto.request.SearchMainUi;
import kware.apps.thirdeye.mobigen.mainui.dto.response.MainUiList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/main-ui")
public class CetusDatasetMainUiRestController {

    private final CetusDatasetMainUiService service;

    @GetMapping("/page")
    public ResponseEntity findDatasetMainUiPage(SearchMainUi search, Pageable pageable) {
        Page<MainUiList> page = service.findDatasetMainUiPage(search, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/check-code")
    public ResponseEntity checkCodeDuplicate(@RequestParam("code") String code) {

        String message = "";
        int findCodeCnt = service.findCountByCode(code);

        if (findCodeCnt >= 1)  message = "이미 등록된 코드입니다. 다른 코드를 입력해주세요";
        else if (findCodeCnt == 0) message = "사용 가능한 코드입니다.";

        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("count", findCodeCnt);
        return ResponseEntity.ok(map);
    }

    @PostMapping
    public ResponseEntity saveDatasetMainUi(@RequestBody SaveMainUi request) {
        service.saveDatasetMainUi(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity deleteDatasetMainUi(@PathVariable("uid") Long uid) {
        service.deleteDatasetMainUi(uid);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{uid}")
    public ResponseEntity changeDatasetMainUi(@PathVariable("uid") Long uid, @RequestBody ChangeMainUi request) {
        service.changeDatasetMainUi(uid, request);
        return ResponseEntity.ok().build();
    }
}
