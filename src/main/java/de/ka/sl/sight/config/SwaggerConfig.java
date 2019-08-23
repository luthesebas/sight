package de.ka.sl.sight.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/** @author Sebastian Luther (https://github.com/luthesebas) */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("de.ka.sl.sight"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
            "sight REST API",
            "This is the official sight REST API.",
            "0.0.1",
            "Terms of service URL",
            new Contact(
                "Sebastian Luther",
                "https://github.com/luthesebas",
                "-"
            ),
            "License of API",
            "API license URL",
            Collections.emptyList()
        );
    }

}
