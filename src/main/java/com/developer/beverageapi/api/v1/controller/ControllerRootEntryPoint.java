package com.developer.beverageapi.api.v1.controller;

import com.developer.beverageapi.api.v1.InstantiateLinks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerRootEntryPoint {

    @Autowired
    private InstantiateLinks instantiateLinks;

    @GetMapping
    public RootEntryPointModel root() {
        var rootEntryPointModel = new RootEntryPointModel();

        rootEntryPointModel.add(instantiateLinks.linkToKitchens("kitchens"));
        rootEntryPointModel.add(instantiateLinks.linkToOrders("orders"));
        rootEntryPointModel.add(instantiateLinks.linkToRestaurants("restaurants"));
        rootEntryPointModel.add(instantiateLinks.linkToGroups("groups"));
        rootEntryPointModel.add(instantiateLinks.linkToUsers("users"));
        rootEntryPointModel.add(instantiateLinks.linkToPermissions("permissions"));
        rootEntryPointModel.add(instantiateLinks.linkToPayment("payments"));
        rootEntryPointModel.add(instantiateLinks.linkToStates("states"));
        rootEntryPointModel.add(instantiateLinks.linkToCities("cities"));
        rootEntryPointModel.add(instantiateLinks.linkToStatistics("statistics"));

        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
    }
}
