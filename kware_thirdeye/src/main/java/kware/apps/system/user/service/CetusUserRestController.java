package kware.apps.system.user.service;

import kware.apps.system.user.dto.request.UserChangeMyInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portal/user")
public class CetusUserRestController {

    private final CetusUserService service;

    @PutMapping("/my-info/{uid}")
    public ResponseEntity changeMyInfo(@PathVariable("uid") Long uid, @RequestBody @Valid UserChangeMyInfo request) {
        service.changeMyInfo(uid, request);
        return ResponseEntity.ok().build();
    }
}
