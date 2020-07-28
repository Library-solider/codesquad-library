package kr.codesquad.library.global.config.properties;

import kr.codesquad.library.global.config.properties.vo.OpenApiParameter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterparkProperties {

    @Value("${interpark.url}")
    private String url;

    @Value("${interpark.key}")
    private String key;

    @Value("${interpark.output}")
    private String output;

    @Value("${interpark.queryType}")
    private String queryType;

    @Bean
    public OpenApiParameter OpenApiParameter() {
        return new OpenApiParameter(url, key, output, queryType);
    }
}
