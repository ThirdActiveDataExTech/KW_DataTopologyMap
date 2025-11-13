package kware.apps.thirdeye.mobigen.datasetuihistory.service;

import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.thirdeye.mobigen.datasetuihistory.dto.request.SearchCetusDatasetHistory;
import kware.apps.thirdeye.mobigen.datasetuihistory.dto.response.CetusDatasetHistoryList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/dataset/mainui-history")
public class CetusDatasetHistoryRestController {

    private final CetusDatasetHistoryService service;

    @GetMapping
    public ResponseEntity findHistoryPageByApprovedUid(SearchCetusDatasetHistory search, Pageable pageable) {
        Page<CetusDatasetHistoryList> page = service.findHistoryPageByApprovedUid(search, pageable);
        return ResponseEntity.ok(page);
    }
}
