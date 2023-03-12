package com.developer.beverageapi.core.springdoc;

import com.developer.beverageapi.api.exceptionHandler.APIError;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

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

    private static final String resourceNotFound = "ResourceNotFound";
    private static final String badRequestResponse = "BadRequestResponse";
    private static final String internalServerError = "InternalServerError";
    private static final String resourceWithoutRepresentation = "ResourceWithoutRepresentation";

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
                .components(new Components().schemas(
                    generateSchemas())
                        .responses(generateResponses()));
    }

    private Map<String, ApiResponse> generateResponses() {

        Map<String, ApiResponse> apiResponseMap = new HashMap<>();

        Content content = new Content()
                .addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().schema(new Schema<APIError>().$ref("APIError")));

        apiResponseMap.put(badRequestResponse, new ApiResponse().description("Invalid request").content(content));

        apiResponseMap.put(resourceNotFound, new ApiResponse().description("Resource not found").content(content));

        apiResponseMap.put(internalServerError, new ApiResponse().description("Internal server error").content(content));

        apiResponseMap.put(resourceWithoutRepresentation, new ApiResponse()
                .description("Resource does not have a representation that can be accepted by the customer").content(content));

        return apiResponseMap;
    }

    private Map<String, Schema> generateSchemas() {
        final Map<String, Schema> schemaMap = new HashMap<>();

        Map<String, Schema> schemaMapAPIError = ModelConverters.getInstance().read(APIError.class);
        Map<String, Schema> schemaObjError = ModelConverters.getInstance().read(APIError.Object.class);

        schemaMap.putAll(schemaMapAPIError);
        schemaMap.putAll(schemaObjError);

        return schemaMap;
    }

    @Bean
    public OpenApiCustomiser openApiCustomiser() {
        return openApi -> {
            openApi.getPaths()
                    .values()
                    .forEach(pathItem -> pathItem.readOperationsMap()
                            .forEach((httpMethod, operation) -> {
                                ApiResponses responses = operation.getResponses();
                                switch (httpMethod) {
                                    case GET:
                                        responses.addApiResponse("406", new ApiResponse().$ref(resourceWithoutRepresentation));
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerError));
                                        break;
                                    case POST:
                                        responses.addApiResponse("400", new ApiResponse().$ref(badRequestResponse));
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerError));
                                        break;
                                    case PUT:
                                        responses.addApiResponse("400", new ApiResponse().$ref(badRequestResponse));
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerError));
                                        break;
                                    case DELETE:
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerError));
                                        break;
                                    default:
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerError));
                                        break;
                                }
                            }));
            /**
             * Below is another way to define status code responses
             */
//                    .stream()
//                    .flatMap(pathItem -> pathItem.readOperations().stream())
//                    .forEach(operation -> {
//                        ApiResponses responses = operation.getResponses();
//
//                        ApiResponse apiNotFound = new ApiResponse().description("Resource not found");
//                        ApiResponse apiWithoutRepresentation = new ApiResponse().description("Resource does not have a " +
//                                "representation that can be accepted by the customer");
//                        ApiResponse apiResponseInternalError = new ApiResponse().description("Internal server error");
//
//                        responses.addApiResponse("500", apiResponseInternalError);
//                        responses.addApiResponse("404", apiNotFound);
//                        responses.addApiResponse("406", apiWithoutRepresentation);
//                    });
        };
    }
}
