package kware.apps.thirdeye.downloadshist.service;


import kware.apps.thirdeye.downloadshist.dto.response.DownloadsHistList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portal/download-hist")
public class CetusDownloadsHistRestController {

    private final CetusDownloadsHistService service;

    @GetMapping
    public ResponseEntity findAllUserDownloadsHistList() {
        List<DownloadsHistList> allUserDownloadsHistPage = service.findAllUserDownloadsHistList();
        return ResponseEntity.ok(allUserDownloadsHistPage);
    }

}
