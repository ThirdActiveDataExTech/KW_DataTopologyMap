package kware.apps.manager.cetus.menu.service;

import kware.apps.manager.cetus.menu.dto.request.MenuListSearch;
import kware.apps.manager.cetus.menu.dto.response.MenuTreeList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manager/api/menu")
public class CetusMenuInfoRestController {

    private final CetusMenuInfoService service;

    @GetMapping("/tree")
    public ResponseEntity tree() {
        List<MenuTreeList> list = service.getMenuTreeList(new MenuListSearch("Y"));
        return ResponseEntity.ok(list);
    }
}
