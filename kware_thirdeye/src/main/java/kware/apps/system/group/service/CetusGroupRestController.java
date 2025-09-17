package kware.apps.system.group.service;


import kware.apps.system.group.dto.request.GroupMergeList;
import kware.apps.system.group.dto.response.GroupListWithUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/system/group")
public class CetusGroupRestController {

    private final CetusGroupService service;

    @GetMapping("/list/{workplaceUid}")
    public ResponseEntity findPositionListByWorkplaceUid(@PathVariable("workplaceUid") Long workplaceUid) {
        List<GroupListWithUser> list = service.findGroupListByWorkplaceUid(workplaceUid);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/merge")
    public ResponseEntity mergePosition(@RequestBody GroupMergeList request) {
        service.mergeGroup(request);
        return ResponseEntity.ok().build();
    }
}
