package com.developer.beverageapi.api.v2.assembler;

import com.developer.beverageapi.api.v2.InstantiateLinksV2;
import com.developer.beverageapi.api.v2.controller.ControllerCityV2;
import com.developer.beverageapi.api.v2.controller.ControllerKitchenV2;
import com.developer.beverageapi.api.v2.model.CityModelV2;
import com.developer.beverageapi.api.v2.model.KitchenModelV2;
import com.developer.beverageapi.domain.model.City;
import com.developer.beverageapi.domain.model.Kitchen;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class KitchenModelAssemblerV2 extends RepresentationModelAssemblerSupport<Kitchen, KitchenModelV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstantiateLinksV2 instantiateLinks;

    public KitchenModelAssemblerV2() {
        super(ControllerKitchenV2.class, KitchenModelV2.class);
    }

    @Override
    public KitchenModelV2 toModel(Kitchen kitchen) {
        KitchenModelV2 kitchenModel = createModelWithId(kitchen.getId(), kitchen);

        modelMapper.map(kitchen, kitchenModel);

        kitchenModel.add(instantiateLinks.linkToKitchens("kitchens"));

        return kitchenModel;
    }

    @Override
    public CollectionModel<KitchenModelV2> toCollectionModel(Iterable<? extends Kitchen> entities) {
        return super.toCollectionModel(entities).add(instantiateLinks.linkToKitchens());
    }
}
