package kr.codesquad.library.global.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "interpark")
public class InterparkProperties {

    private String url;
    private String key;
    private String output;
    private String queryType;
}
