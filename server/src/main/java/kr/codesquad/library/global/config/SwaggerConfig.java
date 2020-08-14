package kr.codesquad.library.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {
    private String title;
    private String contactUrl;

    @Bean
    public Docket api() {
        title = "Library API Document";
        contactUrl = "https://github.com/Library-solider/codesquad-library";

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("kr.codesquad.library.controller"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .apiInfo(apiInfo(title, contactUrl));
    }

    private ApiInfo apiInfo(String title, String contactUrl) {
        return new ApiInfo(
                title,
                "Library Project API Docs",
                "V1",
                contactUrl,
                new Contact("Contact Us", contactUrl, "thstkd92@gmail.com"),
                "Licenses",
                contactUrl,
                new ArrayList<>());
    }
}
