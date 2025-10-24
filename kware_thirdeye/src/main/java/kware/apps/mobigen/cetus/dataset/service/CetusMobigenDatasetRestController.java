package kware.apps.mobigen.cetus.dataset.service;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/mobigen-dataset")
public class CetusMobigenDatasetRestController {

    private final CetusMobigenDatasetService service;


}
