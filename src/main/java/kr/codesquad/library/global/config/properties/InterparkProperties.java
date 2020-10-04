package kr.codesquad.library.global.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "interpark")
public class InterparkProperties {

    private String url;
    private String key;
    private String output;
    private String queryType;
}
