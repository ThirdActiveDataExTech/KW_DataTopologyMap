package kware.apps.system.dept.service;


import kware.apps.system.dept.dto.request.DeptTreeChange;
import kware.apps.system.dept.dto.request.DeptTreeSave;
import kware.apps.system.dept.dto.response.DeptTreeList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/tree/{workplaceUid}")
    public ResponseEntity treeByWorkplaceUid(@PathVariable("workplaceUid") Long workplaceUid) {
        List<DeptTreeList> deptTreeList = service.findDeptTreeListByWorkplaceUid(workplaceUid);
        return ResponseEntity.ok(deptTreeList);
    }

    @PostMapping
    public ResponseEntity saveDeptTree(@RequestBody DeptTreeSave request) {
        service.saveDeptTree(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{uid}")
    public ResponseEntity changeDeptTree(@PathVariable("uid") Long uid, @RequestBody DeptTreeChange request) {
        service.changeDeptTree(uid, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity deleteDeptTree(@PathVariable("uid") Long uid) {
        service.deleteDeptTree(uid);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/root")
    public ResponseEntity saveDeptTreeRoot() {
        service.saveDeptTreeRoot();
        return ResponseEntity.ok().build();
    }
}
