package kware.apps.system.dept.deptuser.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/system/dept-user")
public class CetusDeptUserRestController {

    private final CetusDeptUserService service;

    @GetMapping("/child/{deptUid}")
    public ResponseEntity findDeptUserCount(@PathVariable("deptUid") Long deptUid) {
        int count = service.findDeptUserCount(deptUid);
        return ResponseEntity.ok(count);
    }
}
