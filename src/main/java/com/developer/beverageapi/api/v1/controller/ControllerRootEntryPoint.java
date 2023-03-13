package com.developer.beverageapi.api.v1.controller;

import com.developer.beverageapi.api.v1.InstantiateLinks;
import com.developer.beverageapi.core.security.Security;
import io.swagger.v3.oas.annotations.Operation;
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

    @Autowired
    private Security security;

    @GetMapping
    @Operation(hidden = true)
    public RootEntryPointModel root() {
        var rootEntryPointModel = new RootEntryPointModel();

        if (security.allowedToConsultKitchens()) {
            rootEntryPointModel.add(instantiateLinks.linkToKitchens("kitchens"));
        }

        if (security.allowedToSearchOrders()) {
            rootEntryPointModel.add(instantiateLinks.linkToOrders("orders"));
        }

        if (security.allowedToConsultRestaurants()) {
            rootEntryPointModel.add(instantiateLinks.linkToRestaurants("restaurants"));
        }

        if (security.allowedToConsultUsersGroupsPermissions()) {
            rootEntryPointModel.add(instantiateLinks.linkToGroups("groups"));
            rootEntryPointModel.add(instantiateLinks.linkToUsers("users"));
            rootEntryPointModel.add(instantiateLinks.linkToPermissions("permissions"));
        }

        if (security.allowedToConsultPayments()) {
            rootEntryPointModel.add(instantiateLinks.linkToPayment("payments"));
        }

        if (security.allowedToConsultPayments()) {
            rootEntryPointModel.add(instantiateLinks.linkToStates("states"));
        }

        if (security.allowedToConsultCities()) {
            rootEntryPointModel.add(instantiateLinks.linkToCities("cities"));
        }

        if (security.allowedToConsultStatistics()) {
            rootEntryPointModel.add(instantiateLinks.linkToStatistics("statistics"));
        }

        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
    }
}
