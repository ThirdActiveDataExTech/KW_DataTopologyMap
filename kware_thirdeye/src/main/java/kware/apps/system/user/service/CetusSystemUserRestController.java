package kware.apps.system.user.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.system.user.dto.request.*;
import kware.apps.system.user.dto.response.UserList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/system/user")
public class CetusSystemUserRestController {

    private final CetusUserService service;

    @GetMapping
    public ResponseEntity userPage(@Valid UserListSearch search, Pageable pageable) {
        Page<UserList> page = service.userPage(search, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/excel")
    public ResponseEntity renderUserExcelPage(@Valid UserExcelSearch search) {
        service.renderEXCEL(search);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/change-info")
    public ResponseEntity userChangeInfo(@RequestBody @Valid UserChangeInfo request) {
        service.userChangeInfo(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/change-profile/{uid}")
    public ResponseEntity changeUserProfile(@PathVariable("uid") Long uid, @RequestBody @Valid UserProfile request) {
        service.changeUserProfile(uid, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/change-password/{uid}")
    public ResponseEntity changeUserPassword(@PathVariable("uid") Long uid, @RequestBody @Valid UserPasswordChange request) {
        service.changeUserPassword(uid, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin")
    public ResponseEntity saveUserAdmin(@RequestBody @Valid UserSaveAdmin request) {
        service.saveUserAdmin(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{uid}")
    public ResponseEntity changeUser(@PathVariable("uid") Long uid, @RequestBody @Valid UserChange request) {
        service.changeUser(uid, request);
        return ResponseEntity.ok().build();
    }
}
