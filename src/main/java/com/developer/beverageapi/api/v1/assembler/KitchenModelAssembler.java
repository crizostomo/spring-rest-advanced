package com.developer.beverageapi.api.v1.assembler;

import com.developer.beverageapi.api.v1.InstantiateLinks;
import com.developer.beverageapi.api.v1.controller.ControllerKitchen;
import com.developer.beverageapi.api.v1.model.KitchenModel;
import com.developer.beverageapi.domain.model.Kitchen;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class KitchenModelAssembler extends RepresentationModelAssemblerSupport<Kitchen, KitchenModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstantiateLinks instantiateLinks;

    public KitchenModelAssembler() {
        super(ControllerKitchen.class, KitchenModel.class);
    }

    @Override
    public KitchenModel toModel(Kitchen kitchen) {
        KitchenModel kitchenModel = createModelWithId(kitchen.getId(), kitchen);
        modelMapper.map(kitchen, KitchenModel.class);

        kitchenModel.add(instantiateLinks.linkToKitchens("kitchens"));

        return kitchenModel;
    }
}
