package kware.apps.thirdeye.mobigen.datasetfile.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.thirdeye.mobigen.datasetfile.dto.request.SearchFilePage;
import kware.apps.thirdeye.mobigen.datasetfile.dto.response.CetusDatasetFileList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portal/dataset-file")
public class CetusDatasetFileRestController {

    private final CetusDatasetFileService service;

    @GetMapping("/check-file")
    public ResponseEntity<Boolean> checkFile(final HttpServletRequest req, final HttpServletResponse res) {
        return ResponseEntity.ok(service.checkFile(req));
    }

    @GetMapping("/page")
    public ResponseEntity findMetadataFilePage(SearchFilePage search, Pageable pageable) {
        Page<CetusDatasetFileList> page = service.findMetadataFilePage(search, pageable);
        return ResponseEntity.ok(page);
    }
}
