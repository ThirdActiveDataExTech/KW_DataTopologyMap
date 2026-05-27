package cetus.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.Properties;

public class YamlPropertyLoaderFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource)
        throws IOException {
        
        // 리소스가 존재하지 않으면 빈 Property를 반환하여 앱이 죽는 것을 방지
        if (!encodedResource.getResource().exists()) {
            return new PropertiesPropertySource(
                name != null ? name : (encodedResource.getResource().getFilename() != null ? encodedResource.getResource().getFilename() : "empty"),
                new Properties()
            );
        }

        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(encodedResource.getResource());

        Properties properties = factory.getObject();

        return new PropertiesPropertySource(
            name != null ? name : encodedResource.getResource().getFilename(),
            properties != null ? properties : new Properties()
        );
    }
}