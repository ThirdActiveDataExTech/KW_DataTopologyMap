package kware.apps.asp.contents.service;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kware.apps.asp.contents.domain.CetusCategories;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cetus/api/contents")
public class CetusContentsRestController {

    private final CetusContentsService service;

    @GetMapping("/categories")
    public ResponseEntity categoriesList() {
        List<CetusCategories> allUserStatusHistPage = service.categoriesList();
        return ResponseEntity.ok(allUserStatusHistPage);
    }
}
