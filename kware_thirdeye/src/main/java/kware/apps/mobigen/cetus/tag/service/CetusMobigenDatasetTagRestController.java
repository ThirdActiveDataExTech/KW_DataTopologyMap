package kware.apps.mobigen.cetus.tag.service;

import cetus.annotation.Required;
import kware.apps.mobigen.cetus.tag.dto.request.SearchTag;
import kware.apps.mobigen.cetus.tag.dto.response.TagList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/dataset-tag")
public class CetusMobigenDatasetTagRestController {

    private final CetusMobigenDatasetTagService service;

    @GetMapping("/list")
    public ResponseEntity findMobigenDatasetTagList(SearchTag search) {
        List<TagList> list = service.findMobigenDatasetTagList(search);
        return ResponseEntity.ok(list);
    }
}
