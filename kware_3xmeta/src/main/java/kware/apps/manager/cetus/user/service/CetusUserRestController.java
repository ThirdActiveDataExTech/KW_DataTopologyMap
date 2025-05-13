package kware.apps.manager.cetus.user.service;

import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.manager.cetus.user.dto.request.*;
import kware.apps.manager.cetus.user.dto.response.UserList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value="/cetus/api/user")
@RequiredArgsConstructor
public class CetusUserRestController {

    private final CetusUserService service;

    @PostMapping
    public ResponseEntity saveUser(@RequestBody @Valid UserSave request) {
        service.saveUser(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{uid}")
    public ResponseEntity changeUser(@PathVariable("uid") Long uid, @RequestBody @Valid UserChange request) {
        service.changeUser(uid, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/excel")
    public ResponseEntity renderUserExcelPage(@Valid UserExcelSearch search) {
        service.renderEXCEL(search);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity userPage(@Valid UserListSearch search, Pageable pageable) {
        Page<UserList> page = service.userPage(search, pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/change-info")
    public ResponseEntity userChangeInfo(@RequestBody @Valid UserChangeInfo request) {
        service.userChangeInfo(request);
        return ResponseEntity.ok().build();
    }
}
