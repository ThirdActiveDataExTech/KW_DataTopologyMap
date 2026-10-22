package kware.common.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * springdoc 기본 명세 정보. swagger-ui 는 {@code springdoc.swagger-ui.url} 의 정적 명세(/openapi/spec)를 표시하고,
 * 이 정보는 /v3/api-docs 응답에 쓰인다. 프로파일별 노출 여부는 {@code springdoc.api-docs.enabled} 로 정한다.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI cetusOpenApi() {
        return new OpenAPI().info(new Info()
                .title("CETUS API")
                .description("CETUS API 명세서")
                .version("3.0")
                .contact(new Contact().name("Kware").url("http://www.kware.co.kr")));
    }
}
