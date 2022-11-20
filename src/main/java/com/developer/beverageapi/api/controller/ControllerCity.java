package com.developer.beverageapi.api.controller;

import com.developer.beverageapi.api.assembler.CityInputDismantle;
import com.developer.beverageapi.api.assembler.CityModelAssembler;
import com.developer.beverageapi.api.model.CityModel;
import com.developer.beverageapi.api.model.input.CityInput;
import com.developer.beverageapi.domain.exception.BusinessException;
import com.developer.beverageapi.domain.exception.StateNotFoundException;
import com.developer.beverageapi.domain.model.City;
import com.developer.beverageapi.domain.repository.RepositoryCity;
import com.developer.beverageapi.domain.repository.RepositoryState;
import com.developer.beverageapi.domain.service.CityRegistrationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cities")
@RestController
@RequestMapping("/cities")
public class ControllerCity {

    @Autowired
    private RepositoryCity repositoryCity;

    @Autowired
    private CityRegistrationService registrationCity;

    @Autowired
    private RepositoryState repositoryState;

    @Autowired
    private CityModelAssembler cityModelAssembler;

    @Autowired
    private CityInputDismantle cityInputDismantle;

    @GetMapping
    public List<CityModel> list() {
        List<City> allCities = repositoryCity.findAll();

        return cityModelAssembler.toCollectionModel(allCities);
    }

    @GetMapping("/{cityId}")
    public CityModel search(@PathVariable Long cityId) {
        City city = registrationCity.searchOrFail(cityId);

        return cityModelAssembler.toModel(city);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityModel add(@RequestBody @Valid CityInput cityInput) {
        try {
            City city = cityInputDismantle.toDomainObject(cityInput);

            city = registrationCity.add(city);

            return cityModelAssembler.toModel(city);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cityId}")
    public CityModel update(@PathVariable Long cityId, @RequestBody @Valid CityInput cityInput) {
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
