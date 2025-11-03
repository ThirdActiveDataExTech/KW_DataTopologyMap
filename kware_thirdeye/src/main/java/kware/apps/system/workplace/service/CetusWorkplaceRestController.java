package kware.apps.system.workplace.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.system.workplace.domain.CetusWorkplace;
import kware.apps.system.workplace.dto.request.SearchWorkplace;
import kware.apps.system.workplace.dto.request.WorkplaceSave;
import kware.apps.system.workplace.dto.response.WorkplaceList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.PushBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/system/workplace")
public class CetusWorkplaceRestController {

    private final CetusWorkplaceService service;

    @GetMapping
    public ResponseEntity findWorkplacePage(SearchWorkplace search, Pageable pageable) {
        Page<WorkplaceList> page = service.findWorkplacePage(search, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{uid}")
    public ResponseEntity findWorkplaceByUid(@PathVariable("uid") Long uid) {
        CetusWorkplace bean = service.findWorkplaceByUid(uid);
        return ResponseEntity.ok(bean);
    }

    @PutMapping("/{uid}")
    public ResponseEntity changeWorkplace(@PathVariable("uid") Long uid, @RequestBody WorkplaceSave request) {
        service.changeWorkplace(uid, request);
        return ResponseEntity.ok().build();
    }
}
