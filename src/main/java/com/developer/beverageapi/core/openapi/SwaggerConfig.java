package com.developer.beverageapi.core.openapi;

import com.developer.beverageapi.api.exceptionHandler.APIError;
import com.developer.beverageapi.api.model.CityModel;
import com.developer.beverageapi.api.model.KitchenModel;
import com.developer.beverageapi.api.model.OrderSummaryModel;
import com.developer.beverageapi.api.swaggerapi.model.CitiesModelOpenApi;
import com.developer.beverageapi.api.swaggerapi.model.KitchensModelOpenApi;
import com.developer.beverageapi.api.swaggerapi.model.LinksModelOpenApi;
import com.developer.beverageapi.api.swaggerapi.model.PageableModelOpenApi;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket apiDocket() {
        var typeResolver = new TypeResolver();

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.developer.beverageapi.api")) // It can be used Predicates.and
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
                .globalResponseMessage(RequestMethod.POST, globalGetResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, globalGetResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, globalGetResponseMessages())
                .globalOperationParameters(Arrays.asList(
                        new ParameterBuilder()
                                .name("fields")
                                .description("Properties name to filter in the response, separated by comma") // For SquigglyConfig
                                .parameterType("query")
                                .modelRef(new ModelRef("string"))
                                .build()))
                .additionalModels(typeResolver.resolve(APIError.class))
                .ignoredParameterTypes(ServletWebRequest.class,
                        URL.class, URI.class, URLStreamHandler.class, Resource.class, File.class, InputStream.class)
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class) // It will substitute the pageable inside Controller Kitchen
                .directModelSubstitute(Links.class, LinksModelOpenApi.class)
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(Page.class, KitchenModel.class),
                        KitchensModelOpenApi.class)) // It will substitute the Page properties for KitchenModel
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(Page.class, OrderSummaryModel.class),
                        KitchensModelOpenApi.class)) // It will substitute the Page properties for OrderSummaryModel
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, CityModel.class),
                        CitiesModelOpenApi.class))
                .apiInfo(apiInfo())
                .tags(new Tag("Cities", "It runs cities"),
                        new Tag("Groups", "It runs the users groups"),
                        new Tag("Kitchens", "It runs kitchens"),
                        new Tag("Payments", "It runs payments"),
                        new Tag("Orders", "It runs orders"),
                        new Tag("Restaurants", "It runs restaurants"),
                        new Tag("States", "It runs states"),
                        new Tag("Products", "It runs restaurant products"),
                        new Tag("Users", "It runs users"),
                        new Tag("Statistics", "Beverage Statistics"),
                        new Tag("Permissions", "It describes permissions"));
    }

    private List<ResponseMessage> globalGetResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Internal Error")
                        .responseModel(new ModelRef("APIError"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.NOT_ACCEPTABLE.value())
                        .message("Resource does not has representation acceptable by the client")
                        .build()
        );

    }

    private List<ResponseMessage> globalPostPutResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Invalid Request (client error)")
                        .responseModel(new ModelRef("APIError"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.NOT_ACCEPTABLE.value())
                        .message("Resource does not has representation acceptable by the client")
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Internal Error")
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                        .message("Request refused because the body is in an invalid format")
                        .responseModel(new ModelRef("APIError"))
                        .build()
        );
    }


    private List<ResponseMessage> globalDeleteResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Internal Error")
                        .responseModel(new ModelRef("APIError"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Invalid Request (client error)")
                        .responseModel(new ModelRef("APIError"))
                        .build()
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Beverage Food API")
                .description("Open API for clients")
                .version("1.0")
                .contact(new Contact("WebSite Name", "https://www.toogle.com", "contact@toogle.com"))
                .build();
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath/META-INF/resources/webjars/");
//    }
}
