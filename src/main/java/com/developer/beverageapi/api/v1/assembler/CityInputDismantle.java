package com.developer.beverageapi.api.v1.assembler;

import com.developer.beverageapi.api.v1.model.input.CityInput;
import com.developer.beverageapi.domain.model.City;
import com.developer.beverageapi.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityInputDismantle {

    @Autowired
    private ModelMapper modelMapper;

    public City toDomainObject(CityInput cityInput) {

        return modelMapper.map(cityInput, City.class);
    }

    public void copyToDomainObject(CityInput cityInput, City city) {
        city.setState(new State());

        modelMapper.map(cityInput, city);
    }
}
