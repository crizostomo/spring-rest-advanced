package com.developer.beverageapi.api.assembler;

import com.developer.beverageapi.api.model.input.KitchenInput;
import com.developer.beverageapi.domain.model.Kitchen;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KitchenInputDismantle {

    @Autowired
    private ModelMapper modelMapper;

    public Kitchen toDomainObject(KitchenInput kitchenInput) {

        return modelMapper.map(kitchenInput, Kitchen.class);
    }

    public void copyToDomainObject(KitchenInput kitchenInput, Kitchen kitchen) {
        modelMapper.map(kitchenInput, kitchen);
    }
}
