package kware.apps.system.invite.service;


import kware.apps.system.invite.domain.CetusInvite;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/system/invite")
public class CetusInviteRestController {

    private final CetusInviteService service;

    @PostMapping
    public ResponseEntity sendInvite(@RequestBody CetusInvite request) throws MessagingException {
        service.sendSignupInvite(request);
        return ResponseEntity.ok().build();
    }
}