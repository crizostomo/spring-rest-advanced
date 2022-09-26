package com.developer.beverageapi.core.modelmapper;

import com.developer.beverageapi.api.model.AddressModel;
import com.developer.beverageapi.domain.model.Address;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        /**
         * This is a way to customize an input that is different:
         *
        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Restaurant.class, RestaurantModel.class)
                .addMapping(Restaurant::getDelivery, RestaurantModel::setDelivery);
         *
         */

//        var addressToAddressModelTypeMap = modelMapper.createTypeMap(
//                Address.class, AddressModel.class);
//
//        addressToAddressModelTypeMap.<String>addMapping(
//                sourceAddress -> sourceAddress.getCity().getState().getName(),
//                (destinationAddressModel, value) -> destinationAddressModel.getCity().setState(value));

        return modelMapper;
    }
}
