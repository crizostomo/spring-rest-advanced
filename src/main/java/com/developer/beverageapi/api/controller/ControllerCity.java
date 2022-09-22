package com.developer.beverageapi.api.controller;

import com.developer.beverageapi.domain.exception.BusinessException;
import com.developer.beverageapi.domain.exception.StateNotFoundException;
import com.developer.beverageapi.domain.model.City;
import com.developer.beverageapi.domain.repository.RepositoryCity;
import com.developer.beverageapi.domain.repository.RepositoryState;
import com.developer.beverageapi.domain.service.CityRegistrationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cities")
public class ControllerCity {

    @Autowired
    private RepositoryCity repositoryCity;

    @Autowired
    private CityRegistrationService registrationCity;

    @Autowired
    private RepositoryState repositoryState;

    @GetMapping
    public List<City> list() {
        return repositoryCity.findAll();
    }

    @GetMapping("/{cityId}")
    public City search(@PathVariable Long cityId) {
        return registrationCity.searchOrFail(cityId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public City add(@RequestBody @Valid City city) {
        try {
            return registrationCity.add(city);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cityId}")
    public City update(@PathVariable Long cityId, @RequestBody @Valid City city) {
        City currentCity = registrationCity.searchOrFail(cityId);

        BeanUtils.copyProperties(city, currentCity, "id");

        try {
            return registrationCity.add(currentCity);
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
