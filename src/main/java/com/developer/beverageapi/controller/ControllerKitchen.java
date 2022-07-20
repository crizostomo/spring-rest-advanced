package com.developer.beverageapi.controller;

import com.developer.beverageapi.domain.exception.EntityInUseException;
import com.developer.beverageapi.domain.exception.EntityNotFoundException;
import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.model.XMLWrapperKitchens;
import com.developer.beverageapi.domain.repository.RepositoryKitchen;
import com.developer.beverageapi.domain.service.KitchenRegistrationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return repositoryKitchen.listAll();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public XMLWrapperKitchens listXML() {
        return new XMLWrapperKitchens(repositoryKitchen.listAll());
    }

    //    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/{kitchenId}")
    public ResponseEntity<Kitchen> search(@PathVariable Long kitchenId) {
        Kitchen kitchen = repositoryKitchen.searchById(kitchenId);

        if (kitchen != null) {
            return ResponseEntity.status(HttpStatus.OK).body(kitchen);
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
        Kitchen actualKitchen = repositoryKitchen.searchById(kitchenId);

        if (actualKitchen != null) {

            BeanUtils.copyProperties(kitchen, actualKitchen, "id"); //"It is ignoring the 'id' property

            Kitchen add = repositoryKitchen.add(actualKitchen);
            return ResponseEntity.ok(actualKitchen);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{kitchenId}")
    public ResponseEntity<Kitchen> delete(@PathVariable Long kitchenId) {
        try {
            registrationKitchen.remove(kitchenId);
            return ResponseEntity.noContent().build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();

        } catch (EntityInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
