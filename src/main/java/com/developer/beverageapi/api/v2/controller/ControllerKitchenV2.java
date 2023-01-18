package com.developer.beverageapi.api.v2.controller;

import com.developer.beverageapi.api.v2.assembler.KitchenInputDismantleV2;
import com.developer.beverageapi.api.v2.assembler.KitchenModelAssemblerV2;
import com.developer.beverageapi.api.v2.model.KitchenModelV2;
import com.developer.beverageapi.api.v2.model.input.KitchenInputV2;
import com.developer.beverageapi.api.v2.swaggerapi.controller.ControllerKitchenV2OpenApi;
import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.repository.RepositoryKitchen;
import com.developer.beverageapi.domain.service.KitchenRegistrationService;
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

@RestController
@RequestMapping(value = "/v2/kitchens", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerKitchenV2 implements ControllerKitchenV2OpenApi {

    @Autowired
    private RepositoryKitchen repositoryKitchen;

    @Autowired
    private KitchenRegistrationService registrationKitchen;

    @Autowired
    private KitchenModelAssemblerV2 kitchenModelAssembler;

    @Autowired
    private KitchenInputDismantleV2 kitchenInputDismantle;

    @Autowired
    private PagedResourcesAssembler<Kitchen> pagedResourcesAssembler;

    @GetMapping
    public PagedModel<KitchenModelV2> list(@PageableDefault(size = 10) Pageable pageable) {
        Page<Kitchen> kitchensPaged = repositoryKitchen.findAll(pageable);

        PagedModel<KitchenModelV2> kitchenPagedModel = pagedResourcesAssembler
                .toModel(kitchensPaged, kitchenModelAssembler);

        return kitchenPagedModel;
    }

    @GetMapping("/{kitchenId}")
    public KitchenModelV2 search(@PathVariable Long kitchenId) {
        Kitchen kitchen = registrationKitchen.searchOrFail(kitchenId);

        return kitchenModelAssembler.toModel(kitchen);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenModelV2 add(@RequestBody @Valid KitchenInputV2 kitchenInput) {
        Kitchen kitchen = kitchenInputDismantle.toDomainObject(kitchenInput);
        kitchen = registrationKitchen.add(kitchen);

        return kitchenModelAssembler.toModel(kitchen);
    }

    @PutMapping("/{kitchenId}")
    public KitchenModelV2 update(@PathVariable Long kitchenId,
                                 @RequestBody @Valid KitchenInputV2 kitchenInput) {
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
