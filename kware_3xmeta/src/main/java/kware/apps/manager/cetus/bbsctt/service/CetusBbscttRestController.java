package kware.apps.manager.cetus.bbsctt.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.manager.cetus.bbsctt.dto.request.BbscttSearch;
import kware.apps.manager.cetus.bbsctt.dto.response.BbscttList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cetus/api/bbsctt")
public class CetusBbscttRestController {

    private final CetusBbscttService service;

    @GetMapping
    public ResponseEntity findAllBbscttPage(@Valid BbscttSearch search, Pageable pageable) {
        Page<BbscttList> allBbscttPage = service.findAllBbscttPage(search, pageable);
        return ResponseEntity.ok(allBbscttPage);
    }
}
