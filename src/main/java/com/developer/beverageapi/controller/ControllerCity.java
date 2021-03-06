package com.developer.beverageapi.controller;

import com.developer.beverageapi.domain.exception.EntityInUseException;
import com.developer.beverageapi.domain.exception.EntityNotFoundException;
import com.developer.beverageapi.domain.model.City;
import com.developer.beverageapi.domain.repository.RepositoryCity;
import com.developer.beverageapi.domain.repository.RepositoryState;
import com.developer.beverageapi.domain.service.CityRegistrationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<City> search(@PathVariable Long cityId) {
        Optional<City> city = repositoryCity.findById(cityId);

        if (city.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(city.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody City city) {

        try {
            city = repositoryCity.save(city);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(city);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{cityId}")
    public ResponseEntity<City> update(@PathVariable Long cityId, @RequestBody City city) {
        Optional<City> currentCity = repositoryCity.findById(cityId);

        try {
            if (currentCity.isPresent()) {
                BeanUtils.copyProperties(city, currentCity, "id");

                City savedCity = registrationCity.add(currentCity.get());
                return ResponseEntity.ok(savedCity);
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{cityId}")
    public ResponseEntity<City> delete(@PathVariable Long cityId) {
        try {
            registrationCity.remove(cityId);
            return ResponseEntity.noContent().build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();

        } catch (EntityInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
