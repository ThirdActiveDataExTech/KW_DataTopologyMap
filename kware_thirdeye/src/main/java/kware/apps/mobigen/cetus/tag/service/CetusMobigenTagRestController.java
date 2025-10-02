package kware.apps.mobigen.cetus.tag.service;


import kware.apps.mobigen.cetus.tag.dto.response.TagList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portal/mobigen-tag")
public class CetusMobigenTagRestController {

    private final CetusMobigenTagService service;

    @GetMapping("/list")
    public ResponseEntity findMobigenTagList() {
        List<TagList> list = service.findMobigenTagList();
        return ResponseEntity.ok(list);
    }
}
