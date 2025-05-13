package kware.apps.manager.cetus.downloadshist.service;


import kware.apps.manager.cetus.downloadshist.dto.response.CetusDownloadsHistList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cetus/api/download-hist")
public class CetusDownloadsHistRestController {

    private final CetusDownloadsHistService service;

    @GetMapping("/list")
    public ResponseEntity findAllUserDownloadsHist() {
        List<CetusDownloadsHistList> allUserDownloadsHist = service.findAllUserDownloadsHist();
        return ResponseEntity.ok(allUserDownloadsHist);
    }

}
