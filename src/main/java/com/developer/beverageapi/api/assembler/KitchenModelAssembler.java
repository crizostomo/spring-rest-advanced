package com.developer.beverageapi.api.assembler;

import com.developer.beverageapi.api.controller.ControllerKitchen;
import com.developer.beverageapi.api.model.KitchenModel;
import com.developer.beverageapi.domain.model.Kitchen;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class KitchenModelAssembler extends RepresentationModelAssemblerSupport<Kitchen, KitchenModel> {

    @Autowired
    private ModelMapper modelMapper;

    public KitchenModelAssembler() {
        super(ControllerKitchen.class, KitchenModel.class);
    }

    @Override
    public KitchenModel toModel(Kitchen kitchen) {
        KitchenModel kitchenModel = createModelWithId(kitchen.getId(), kitchen);
        modelMapper.map(kitchen, KitchenModel.class);

        kitchenModel.add(WebMvcLinkBuilder.linkTo(ControllerKitchen.class).withRel("kitchens"));

        return kitchenModel;
    }
}
