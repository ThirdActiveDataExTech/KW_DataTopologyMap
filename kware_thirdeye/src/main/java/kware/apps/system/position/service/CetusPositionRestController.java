package kware.apps.system.position.service;


import kware.apps.system.position.dto.request.PositionMergeList;
import kware.apps.system.position.dto.response.PositionList;
import kware.apps.system.position.dto.response.PositionListWithUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/system/position")
public class CetusPositionRestController {

    private final CetusPositionService service;

    @GetMapping("/list/{workplaceUid}")
    public ResponseEntity findPositionListByWorkplaceUid(@PathVariable("workplaceUid") Long workplaceUid) {
        List<PositionListWithUser> list = service.findPositionListByWorkplaceUid(workplaceUid);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/merge")
    public ResponseEntity mergePosition(@RequestBody PositionMergeList request) {
        service.mergePosition(request);
        return ResponseEntity.ok().build();
    }
}
