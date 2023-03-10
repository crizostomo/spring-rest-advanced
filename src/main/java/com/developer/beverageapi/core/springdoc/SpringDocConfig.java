package com.developer.beverageapi.core.springdoc;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@SecurityScheme(name = "security_auth",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(authorizationCode = @OAuthFlow(
                authorizationUrl = "${springdoc.oAuthFlow.authorizationUrl}",
                tokenUrl = "${springdoc.oAuthFlow.tokenUrl}",
                scopes = {
                        @OAuthScope(name = "READ", description = "read scope"),
                        @OAuthScope(name = "WRITE", description = "write scope")
                }
        )))
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Beverage API")
                        .version("v1")
                        .description("Beverage REST API")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("External Documentation for Beverage API")
                        .url("https://springdoc.com"))
//                .tags(Arrays.asList(
//                        new Tag().name("Cities").description("It runs Citiies")))
                ;
    }

    @Bean
    public OpenApiCustomiser openApiCustomiser() {
        return openApi -> {
            openApi.getPaths()
                    .values()
                    .stream()
                    .flatMap(pathItem -> pathItem.readOperations().stream())
                    .forEach(operation -> {
                        ApiResponses responses = operation.getResponses();

                        ApiResponse apiNotFound = new ApiResponse().description("Resource not found");
                        ApiResponse apiWithoutRepresentation = new ApiResponse().description("Resource does not have a " +
                                "representation that can be accepted by the customer");
                        ApiResponse apiResponseInternalError = new ApiResponse().description("Internal server error");

                        responses.addApiResponse("500", apiResponseInternalError);
                        responses.addApiResponse("404", apiNotFound);
                        responses.addApiResponse("406", apiWithoutRepresentation);
                    });
        };
    }
}
