package com.developer.beverageapi.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.mediatype.hal.HalConfiguration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class BeverageHalConfig {

    @Bean
    public HalConfiguration globalPolicy() {
        return new HalConfiguration()
                .withMediaType(MediaType.APPLICATION_JSON)
                .withMediaType(BeverageMediaTypes.V1_APPLICATION_JSON)
                .withMediaType(BeverageMediaTypes.V2_APPLICATION_JSON);
    }
}