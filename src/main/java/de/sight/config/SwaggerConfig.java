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
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName(environment.getProperty("sight.project.artifact-id"))
            .produces(producesMediaTypes())
            .consumes(consumesMediaTypes())
            .select()
            .apis(RequestHandlerSelectors.basePackage(
                environment.getProperty("sight.project.group-id")
            ))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo () {
        return new ApiInfo(
            environment.getProperty("sight.project.name"),
            environment.getProperty("sight.project.description"),
            environment.getProperty("sight.project.version"),
            "Terms of service URL",
            new Contact(
                environment.getProperty("sight.contact.name"),
                environment.getProperty("sight.contact.url"),
                environment.getProperty("sight.contact.email")
            ),
            "API License",
            "API License URL",
            Collections.emptyList()
        );
    }

    private Set<String> producesMediaTypes () {
        Set<String> mediaTypes = new HashSet<>();
        mediaTypes.add("application/hal+json");
        return mediaTypes;
    }

    private Set<String> consumesMediaTypes () {
        Set<String> mediaTypes = new HashSet<>();
        mediaTypes.add("application/json");
        return mediaTypes;
    }

}
