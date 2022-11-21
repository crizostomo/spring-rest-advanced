package com.developer.beverageapi.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket apiDocket() {
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
                .apiInfo(apiInfo())
                .tags(new Tag("Cities", "It runs cities"));
    }

    private List<ResponseMessage> globalGetResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Internal Error")
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
                        .build()
        );
    }


    private List<ResponseMessage> globalDeleteResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Internal Error")
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Invalid Request (client error)")
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
