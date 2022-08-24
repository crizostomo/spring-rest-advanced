package com.developer.beverageapi.controller;

import com.developer.beverageapi.domain.exception.EntityInUseException;
import com.developer.beverageapi.domain.exception.EntityNotFoundException;
import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.repository.RepositoryKitchen;
import com.developer.beverageapi.domain.service.KitchenRegistrationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/kitchens", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerKitchen {

    @Autowired
    private RepositoryKitchen repositoryKitchen;

    @Autowired
    private KitchenRegistrationService registrationKitchen;

    //    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @GetMapping
    public List<Kitchen> list() {
        return repositoryKitchen.findAll();
    }

    //    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/{kitchenId}")
    public ResponseEntity<Kitchen> search(@PathVariable Long kitchenId) {
        Optional<Kitchen> kitchen = repositoryKitchen.findById(kitchenId);

        if (kitchen.isPresent()) {
            return ResponseEntity.ok(kitchen.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Kitchen add(@RequestBody Kitchen kitchen) {
        return registrationKitchen.add(kitchen);
    }

    @PutMapping("/{kitchenId}")
    public ResponseEntity<Kitchen> update(@PathVariable Long kitchenId,
                                          @RequestBody Kitchen kitchen) {
        Optional<Kitchen> currentKitchen = repositoryKitchen.findById(kitchenId);

        if (currentKitchen.isPresent()) {
            BeanUtils.copyProperties(kitchen, currentKitchen.get(), "id"); //"It is ignoring the 'id' property

            Kitchen savedKitchen = registrationKitchen.add(currentKitchen.get());
            return ResponseEntity.ok(savedKitchen);
        }
        return ResponseEntity.notFound().build();
    }

//    @DeleteMapping("/{kitchenId}")
//    public ResponseEntity<Kitchen> delete(@PathVariable Long kitchenId) {
//        try {
//            registrationKitchen.remove(kitchenId);
//            return ResponseEntity.noContent().build();
//
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.notFound().build();
//
//        } catch (EntityInUseException e) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        }
//    }

    @DeleteMapping("/{kitchenId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long kitchenId) {

        registrationKitchen.remove(kitchenId);
    }
}
