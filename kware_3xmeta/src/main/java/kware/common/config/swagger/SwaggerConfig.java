package kware.common.config.swagger;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;


/**
 * org.springframework.boot:spring-boot-starter-actuator와 약간 충둘이 있네
 * 찾아보면 mvc.pathmatch.matching-strategy: ANT_PATH_MATCHER actuator에서 무효화 시키는 듯 하네..
 * 
 * actouator을 설정하면 하위의 주석을 해제하고 @EnableWebMvc 까지 설정을 해야 swagger가 동작함.
 * @EnableWebMvc 설정관련
 *   mvc.pathmatch.matching-strategy: ANT_PATH_MATCHER 설정하면  @EnableWebMvc를 설정이 필요없고,
 *   설정하지 않고 할경우에는 @EnableWebMvc를 설정이 필요. 오류는 없으나 동작을 안함
 * 
 * actuator를 설정하지않으면 현재 하위 주석 유지하고  
 * yml에 mvc.pathmatch.matching-strategy: ANT_PATH_MATCHER 설정하면 동작한다.
 * 
 * swagger를 운영서버에서 동작을 중지하고 싶은경우
 * yml 파일에 springfox.documentation.enabled: false 설정한다. default는 true임.
 * 
 * @author kljang
 *
 */
@Configuration
public class SwaggerConfig {

	@Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("kware.apps.asp.swagger"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("CETUS Swagger")
                .contact(new Contact("Kware", "http://www.kware.co.kr", "kware123@kware.co.kr"))
                .description("CETUS API 명세서")
                .version("3.0")
                .build();
    }
    
    /* actuator가 설정되면 필요하고, 없으면 주석으로 처리해도 됨 
     * mvc.pathmatch.matching-strategy: ANT_PATH_MATCHER actuator에서 무효화 시키는 듯*/
    
    @Bean
    public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return new BeanPostProcessor() {

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider ) {
                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
                }
                return bean;
            }

            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
                List<T> copy = mappings.stream()
                    .filter(mapping -> mapping.getPatternParser() == null)
                    .collect(Collectors.toList());
                mappings.clear();
                mappings.addAll(copy);
            }

            @SuppressWarnings("unchecked")
            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
                try {
                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                    field.setAccessible(true);
                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        };
    }
      
}