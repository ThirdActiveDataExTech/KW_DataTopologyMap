package kware.apps.system.dept.service;


import kware.apps.system.dept.dto.response.DeptTreeList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/system/dept")
public class CetusDeptRestController {

    private final CetusDeptService service;

    @GetMapping("/tree")
    public ResponseEntity tree() {
        List<DeptTreeList> deptTreeList = service.findDeptTreeList();
        return ResponseEntity.ok(deptTreeList);
    }
}
