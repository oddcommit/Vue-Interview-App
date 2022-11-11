package com.tristanruecker.interviewexampleproject.config.documentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.util.Predicates;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;


@Configuration
@Profile("development")
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .paths(Predicates.negate(PathSelectors.regex("/error.*")))
                .build();
    }




//    @Bean
//    public Docket productApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.tristanruecker.interviewexampleproject"))
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(metaInfo());
//    }
//
//    private ApiInfo metaInfo() {
//        ApiInfo apiInfo = new ApiInfo(
//                "Cursos API REST",
//                "API REST de registro de alunos.",
//                "1.0",
//                "Terms of Service",
//                new Contact("Romulo Sorato", "https://www.linkedin.com/in/r%C3%B4mulo-sorato-domingos-a6524437/",
//                        "romulosorato@hotmail.com"),
//                "Apache License Version 2.0",
//                "https://www.apache.org/licesen.html", new ArrayList<>()
//        );
//
//        return apiInfo;
//    }

}

