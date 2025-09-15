package kware.apps.thirdeye.comment.service;

import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.thirdeye.comment.dto.request.DatasetCommentSave;
import kware.apps.thirdeye.comment.dto.request.DatasetCommentSearch;
import kware.apps.thirdeye.comment.dto.response.DatasetCommentList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portal/dataset-comment")
public class CetusDatasetCommentRestController {

    private final CetusDatasetCommentService service;

    @GetMapping("/type-each")
    public ResponseEntity findDatasetCommentCntByType(DatasetCommentSearch search) {
        Map<String, Integer> map = service.findDatasetCommentCntByType(search);
        return ResponseEntity.ok(map);
    }

    @GetMapping
    public ResponseEntity findDatasetCommentPage(DatasetCommentSearch search, Pageable pageable) {
        Page<DatasetCommentList> page = service.findDatasetCommentPage(search, pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity saveDatasetComment(@RequestBody DatasetCommentSave request) {
        service.saveDatasetComment(request);
        return ResponseEntity.ok().build();
    }
}
