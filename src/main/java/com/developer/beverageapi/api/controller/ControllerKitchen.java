package com.developer.beverageapi.api.controller;

import com.developer.beverageapi.api.assembler.KitchenInputDismantle;
import com.developer.beverageapi.api.assembler.KitchenModelAssembler;
import com.developer.beverageapi.api.model.KitchenModel;
import com.developer.beverageapi.api.model.input.KitchenInput;
import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.repository.RepositoryKitchen;
import com.developer.beverageapi.domain.service.KitchenRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/kitchens", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerKitchen {

    @Autowired
    private RepositoryKitchen repositoryKitchen;

    @Autowired
    private KitchenRegistrationService registrationKitchen;

    @Autowired
    private KitchenModelAssembler kitchenModelAssembler;

    @Autowired
    private KitchenInputDismantle kitchenInputDismantle;

    //    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @GetMapping
    public List<KitchenModel> list() {
        List<Kitchen> allKitchens = repositoryKitchen.findAll();

        return kitchenModelAssembler.toCollectionModel(allKitchens);
    }

    //    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/{kitchenId}")
    public KitchenModel search(@PathVariable Long kitchenId) {
        Kitchen kitchen = registrationKitchen.searchOrFail(kitchenId);

        return kitchenModelAssembler.toModel(kitchen);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenModel add(@RequestBody @Valid KitchenInput kitchenInput) {
        Kitchen kitchen = kitchenInputDismantle.toDomainObject(kitchenInput);
        kitchen = registrationKitchen.add(kitchen);

        return kitchenModelAssembler.toModel(kitchen);
    }

    @PutMapping("/{kitchenId}")
    public KitchenModel update(@PathVariable Long kitchenId,
                          @RequestBody @Valid KitchenInput kitchenInput) {
        Kitchen currentKitchen = registrationKitchen.searchOrFail(kitchenId);
        kitchenInputDismantle.copyToDomainObject(kitchenInput, currentKitchen);
        currentKitchen = registrationKitchen.add(currentKitchen);

        return kitchenModelAssembler.toModel(currentKitchen);
    }

    @DeleteMapping("/{kitchenId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long kitchenId) {
        registrationKitchen.remove(kitchenId);
    }
}