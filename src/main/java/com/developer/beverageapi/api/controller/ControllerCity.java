package com.developer.beverageapi.api.controller;

import com.developer.beverageapi.api.assembler.CityInputDismantle;
import com.developer.beverageapi.api.assembler.CityModelAssembler;
import com.developer.beverageapi.api.exceptionHandler.APIError;
import com.developer.beverageapi.api.model.CityModel;
import com.developer.beverageapi.api.model.input.CityInput;
import com.developer.beverageapi.domain.exception.BusinessException;
import com.developer.beverageapi.domain.exception.StateNotFoundException;
import com.developer.beverageapi.domain.model.City;
import com.developer.beverageapi.domain.repository.RepositoryCity;
import com.developer.beverageapi.domain.repository.RepositoryState;
import com.developer.beverageapi.domain.service.CityRegistrationService;
import io.swagger.annotations.*;
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

    @ApiOperation(value = "List cities")
    @GetMapping
    public List<CityModel> list() {
        List<City> allCities = repositoryCity.findAll();

        return cityModelAssembler.toCollectionModel(allCities);
    }

    @ApiOperation(value = "Search a city by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "City id invalid", response = APIError.class),
            @ApiResponse(code = 404, message = "City not found", response = APIError.class)
    })
    @GetMapping("/{cityId}")
    public CityModel search(@ApiParam(value = "City Id", example = "1")
                                @PathVariable Long cityId) {
        City city = registrationCity.searchOrFail(cityId);

        return cityModelAssembler.toModel(city);
    }

    @ApiOperation(value = "It records a city")
    @ApiResponses({
            @ApiResponse(code = 201, message = "City created")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityModel add(@ApiParam(name = "body", value = "City Representation")
                             @RequestBody @Valid CityInput cityInput) {
        try {
            City city = cityInputDismantle.toDomainObject(cityInput);

            city = registrationCity.add(city);

            return cityModelAssembler.toModel(city);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @ApiOperation(value = "It updates a city by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "City updated"),
            @ApiResponse(code = 404, message = "City not found", response = APIError.class)
    })
    @PutMapping("/{cityId}")
    public CityModel update(@ApiParam(value = "City Id", example = "1") @PathVariable Long cityId,
                            @ApiParam(name = "body", value = "City Representation with new data")
                            @RequestBody @Valid CityInput cityInput) {
        try {
            City currentCity = registrationCity.searchOrFail(cityId);

            cityInputDismantle.copyToDomainObject(cityInput, currentCity);

            currentCity = registrationCity.add(currentCity);

            return cityModelAssembler.toModel(currentCity);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }


    @ApiOperation(value = "It deletes a city by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "City deleted"),
            @ApiResponse(code = 404, message = "City not found", response = APIError.class)
    })
    @DeleteMapping("/{cityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@ApiParam(value = "City Id", example = "1")
                           @PathVariable Long cityId) {
        registrationCity.remove(cityId);
    }
}
