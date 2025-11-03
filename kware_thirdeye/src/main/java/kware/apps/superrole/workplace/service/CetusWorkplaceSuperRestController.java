package kware.apps.superrole.workplace.service;

import kware.apps.superrole.workplace.dto.request.SetWorkplace;
import kware.apps.superrole.workplace.dto.request.createworkplace.CreateWorkplace;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/super/workplace")
public class CetusWorkplaceSuperRestController {

    private final CetusWorkplaceSuperService service;

    @PostMapping("/choose")
    public ResponseEntity chooseWorkplace(@RequestBody SetWorkplace request) {
        service.setSuperUserWorkplace(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity createWorkplace(@RequestBody CreateWorkplace request) {
        service.createWorkplace(request);
        return ResponseEntity.ok().build();
    }
}
