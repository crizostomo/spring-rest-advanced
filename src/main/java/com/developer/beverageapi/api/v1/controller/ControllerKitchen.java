package com.developer.beverageapi.api.v1.controller;

import com.developer.beverageapi.api.v1.assembler.KitchenInputDismantle;
import com.developer.beverageapi.api.v1.assembler.KitchenModelAssembler;
import com.developer.beverageapi.api.v1.model.KitchenModel;
import com.developer.beverageapi.api.v1.model.input.KitchenInput;
import com.developer.beverageapi.api.v1.swaggerapi.controller.ControllerKitchenOpenApi;
import com.developer.beverageapi.core.security.CheckSecurity;
import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.repository.RepositoryKitchen;
import com.developer.beverageapi.domain.service.KitchenRegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/v1/kitchens", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerKitchen implements ControllerKitchenOpenApi {

    @Autowired
    private RepositoryKitchen repositoryKitchen;

    @Autowired
    private KitchenRegistrationService registrationKitchen;

    @Autowired
    private KitchenModelAssembler kitchenModelAssembler;

    @Autowired
    private KitchenInputDismantle kitchenInputDismantle;

    @Autowired
    private PagedResourcesAssembler<Kitchen> pagedResourcesAssembler;

    //    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @CheckSecurity.Kitchens.AllowedToConsult
    @Override
    @GetMapping
    public PagedModel<KitchenModel> list(@PageableDefault(size = 10) Pageable pageable) {
        Page<Kitchen> kitchensPaged = repositoryKitchen.findAll(pageable);

        PagedModel<KitchenModel> kitchenPagedModel = pagedResourcesAssembler
                .toModel(kitchensPaged, kitchenModelAssembler);

        return kitchenPagedModel;
    }

    //    @ResponseStatus(HttpStatus.CREATED)
    @CheckSecurity.Kitchens.AllowedToConsult
    @Override
    @GetMapping("/{kitchenId}")
    public KitchenModel search(@PathVariable Long kitchenId) {
        Kitchen kitchen = registrationKitchen.searchOrFail(kitchenId);

        return kitchenModelAssembler.toModel(kitchen);
    }

    @CheckSecurity.Kitchens.AllowedToEdit
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenModel add(@RequestBody @Valid KitchenInput kitchenInput) {
        Kitchen kitchen = kitchenInputDismantle.toDomainObject(kitchenInput);
        kitchen = registrationKitchen.add(kitchen);

        return kitchenModelAssembler.toModel(kitchen);
    }

    @CheckSecurity.Kitchens.AllowedToEdit
    @Override
    @PutMapping("/{kitchenId}")
    public KitchenModel update(@PathVariable Long kitchenId,
                               @RequestBody @Valid KitchenInput kitchenInput) {
        Kitchen currentKitchen = registrationKitchen.searchOrFail(kitchenId);
        kitchenInputDismantle.copyToDomainObject(kitchenInput, currentKitchen);
        currentKitchen = registrationKitchen.add(currentKitchen);

        return kitchenModelAssembler.toModel(currentKitchen);
    }

    @CheckSecurity.Kitchens.AllowedToEdit
    @Override
    @DeleteMapping("/{kitchenId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long kitchenId) {
        registrationKitchen.remove(kitchenId);
    }
}
