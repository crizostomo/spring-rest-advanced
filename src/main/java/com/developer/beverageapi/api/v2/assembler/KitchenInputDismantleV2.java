package com.developer.beverageapi.api.v2.assembler;

import com.developer.beverageapi.api.v2.model.input.KitchenInputV2;
import com.developer.beverageapi.domain.model.Kitchen;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KitchenInputDismantleV2 {

    @Autowired
    private ModelMapper modelMapper;

    public Kitchen toDomainObject(KitchenInputV2 kitchenInput) {
        return modelMapper.map(kitchenInput, Kitchen.class);
    }

    public void copyToDomainObject(KitchenInputV2 kitchenInput, Kitchen kitchen) {
        modelMapper.map(kitchenInput, kitchen);
    }
}
