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

    /**
     *
     * 데이터셋 목록 페이지, 연관/관계 데이터 페이지에서 검색을 통한 태그 목록 조회
     *
     * @api         [GET] /api/portal/mobigen-tag
     * @author      dahyeon
     * @date        2025-10-15
    **/
    @GetMapping("/list")
    public ResponseEntity findMobigenTagList() {
        List<TagList> list = service.findMobigenTagList();
        return ResponseEntity.ok(list);
    }
}
