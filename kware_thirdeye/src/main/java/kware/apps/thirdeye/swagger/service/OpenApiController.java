package kware.apps.thirdeye.swagger.service;

import java.nio.charset.StandardCharsets;


import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/openapi")
public class OpenApiController {

    @GetMapping(value = "/spec", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getOpenApiSpec() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/api-docs/openapi.json");
        InputStream inputStream = resource.getInputStream();
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

    }
}
