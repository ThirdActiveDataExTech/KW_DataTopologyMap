package kware.apps.manager.cetus.downloadshist.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.manager.cetus.downloadshist.dto.request.DownloadsHistSearch;
import kware.apps.manager.cetus.downloadshist.dto.response.DownloadsHistList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cetus/api/download-hist")
public class CetusDownloadsHistRestController {

    private final CetusDownloadsHistService service;

    @GetMapping
    public ResponseEntity findAllUserDownloadsHistPage(@Valid DownloadsHistSearch search, Pageable pageable) {
        Page<DownloadsHistList> allUserDownloadsHistPage = service.findAllUserDownloadsHistPage(search, pageable);
        return ResponseEntity.ok(allUserDownloadsHistPage);
    }

}
