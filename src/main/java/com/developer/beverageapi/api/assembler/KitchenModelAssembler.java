package com.developer.beverageapi.api.assembler;

import com.developer.beverageapi.api.model.KitchenModel;
import com.developer.beverageapi.domain.model.Kitchen;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class KitchenModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public KitchenModel toModel(Kitchen kitchen) {

        return modelMapper.map(kitchen, KitchenModel.class);
    }

    public List<KitchenModel> toCollectionModel(List<Kitchen> kitchens) {
        return kitchens.stream()
                .map(kitchen -> toModel(kitchen))
                .collect(Collectors.toList());
    }
}
