package com.developer.beverageapi.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        /**
         * This is a way to customize an input that is different:
         *
        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Restaurant.class, RestaurantModel.class)
                .addMapping(Restaurant::getDelivery, RestaurantModel::setDelivery);
         *
         */

        return new ModelMapper();
    }
}
