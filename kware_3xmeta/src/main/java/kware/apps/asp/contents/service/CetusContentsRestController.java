package kware.apps.asp.contents.service;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.asp.contents.domain.CetusCategories;
import kware.apps.asp.contents.dto.request.ContentsSearch;
import kware.apps.asp.contents.dto.response.ContentsPage;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cetus/api/contents")
public class CetusContentsRestController {

    private final CetusContentsService service;

    @GetMapping("/categories")
    public ResponseEntity categoriesList() {
        List<CetusCategories> categoriesList = service.categoriesList();
        return ResponseEntity.ok(categoriesList);
    }

    @GetMapping
    public ResponseEntity getContentPageList(ContentsSearch search, Pageable pageable) {
        Page<ContentsPage> contentPageList = service.getContentPageList(search, pageable);
        return ResponseEntity.ok(contentPageList);
    }
}
