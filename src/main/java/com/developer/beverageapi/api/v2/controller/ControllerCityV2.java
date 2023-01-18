package com.developer.beverageapi.api.v2.controller;

import com.developer.beverageapi.api.ResourceUriHelper;
import com.developer.beverageapi.api.v2.assembler.CityInputDismantleV2;
import com.developer.beverageapi.api.v2.assembler.CityModelAssemblerV2;
import com.developer.beverageapi.api.v2.model.CityModelV2;
import com.developer.beverageapi.api.v2.model.input.CityInputV2;
import com.developer.beverageapi.core.web.BeverageMediaTypes;
import com.developer.beverageapi.domain.exception.BusinessException;
import com.developer.beverageapi.domain.exception.StateNotFoundException;
import com.developer.beverageapi.domain.model.City;
import com.developer.beverageapi.domain.repository.RepositoryCity;
import com.developer.beverageapi.domain.repository.RepositoryState;
import com.developer.beverageapi.domain.service.CityRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
//@RequestMapping(value = "/cities", produces = BeverageMediaTypes.V2_APPLICATION_JSON_VALUE)
@RequestMapping(value = "/v2/cities", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerCityV2 {

    @Autowired
    private RepositoryCity repositoryCity;

    @Autowired
    private CityRegistrationService registrationCity;

    @Autowired
    private RepositoryState repositoryState;

    @Autowired
    private CityModelAssemblerV2 cityModelAssembler;

    @Autowired
    private CityInputDismantleV2 cityInputDismantle;

    @GetMapping
    public CollectionModel<CityModelV2> list() {
        List<City> allCities = repositoryCity.findAll();

        return cityModelAssembler.toCollectionModel(allCities);
    }

    @GetMapping("/{cityId}")
    public CityModelV2 search(@PathVariable Long cityId) {
        City city = registrationCity.searchOrFail(cityId);

        return cityModelAssembler.toModel(city);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityModelV2 add(@RequestBody @Valid CityInputV2 cityInput) {
        try {
            City city = cityInputDismantle.toDomainObject(cityInput);

            city = registrationCity.add(city);

            CityModelV2 cityModel = cityModelAssembler.toModel(city);

            ResourceUriHelper.addUriInResponseHeader(cityModel.getCityId());

            return cityModel;
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cityId}")
    public CityModelV2 update(@PathVariable Long cityId, @RequestBody @Valid CityInputV2 cityInput) {
        try {
            City currentCity = registrationCity.searchOrFail(cityId);

            cityInputDismantle.copyToDomainObject(cityInput, currentCity);

            currentCity = registrationCity.add(currentCity);

            return cityModelAssembler.toModel(currentCity);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long cityId) {
        registrationCity.remove(cityId);
    }
}
