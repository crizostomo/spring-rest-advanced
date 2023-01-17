package com.developer.beverageapi.core.modelmapper;

import com.developer.beverageapi.api.v1.model.AddressModel;
import com.developer.beverageapi.api.v1.model.input.OrderItemInput;
import com.developer.beverageapi.api.v2.model.input.CityInputV2;
import com.developer.beverageapi.domain.model.Address;
import com.developer.beverageapi.domain.model.City;
import com.developer.beverageapi.domain.model.OrderItem;
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

        modelMapper.createTypeMap(OrderItemInput.class, OrderItem.class)
                .addMappings(mapper -> mapper.skip(OrderItem::setId));

        modelMapper.createTypeMap(CityInputV2.class, City.class)
                .addMappings(mapper -> mapper.skip(City::setId)); // To avoid a wrong update if you set cityId "1"

        var addressToModelAddressTypeMap = modelMapper.createTypeMap(
                Address.class, AddressModel.class);

        addressToModelAddressTypeMap.<String>addMapping(
                addressSrc -> addressSrc.getCity().getState().getName(),
                (addressModelDest, value) -> addressModelDest.getCity().setStateName(value));

        return modelMapper;
    }
}
