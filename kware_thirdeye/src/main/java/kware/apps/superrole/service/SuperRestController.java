package kware.apps.superrole.service;

import kware.apps.superrole.dto.request.SetWorkplace;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/super")
public class SuperRestController {

    private final SuperService service;

    @PostMapping("/workplace-choose")
    public ResponseEntity chooseWorkplace(@RequestBody SetWorkplace request) {
        service.setSuperUserWorkplace(request);
        return ResponseEntity.ok().build();
    }
}
