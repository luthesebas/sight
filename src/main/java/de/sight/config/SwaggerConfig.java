package de.sight.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/** @author Sebastian Luther (https://github.com/luthesebas) */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private Environment environment;

    @Bean
    public Docket api () {
        Set<String> produces = new HashSet<>();
        produces.add("application/hal+json");
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json");

        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("sight-api")
            .produces(produces)
            .consumes(consumes)
            .select()
            .apis(RequestHandlerSelectors.basePackage("de.sight.rest"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo () {
        //TODO Extract to application.properties
        return new ApiInfo(
            environment.getProperty("app.info.name"),
            environment.getProperty("app.info.description"),
            environment.getProperty("app.info.version"),
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
