package com.tristanruecker.interviewexampleproject.config.documentation;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Access via link: <a href="http://localhost:4667/api/swagger-ui/index.html#/">...</a>
 */
@Configuration
@Profile("development")
public class SwaggerConfig {
    private final String apiName = "interview-example-project-api";

    /**
     * Used to configure oauth for swagger
     * @return OpenAPI
     */
    @Bean
    public OpenAPI customOpenAPI() {
        String securitySchemeName = "bearerAuth";
        String apiTitle = String.format("%s API", StringUtils.capitalize(apiName));

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .info(new Info().title(apiTitle).version("1.0.0"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group(apiName)
                .pathsToMatch("/**")
                .build();
    }

}

