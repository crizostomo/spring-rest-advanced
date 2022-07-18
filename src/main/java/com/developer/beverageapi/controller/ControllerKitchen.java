package com.developer.beverageapi.controller;

import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.model.XMLWrapperKitchens;
import com.developer.beverageapi.domain.repository.RepositoryKitchen;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @GetMapping
    public List<Kitchen> list(){
        return repositoryKitchen.listAll();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public XMLWrapperKitchens listXML(){
        return new XMLWrapperKitchens(repositoryKitchen.listAll());
    }

//    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/{kitchenId}")
    public ResponseEntity<Kitchen> search(@PathVariable Long kitchenId){
        Kitchen kitchen = repositoryKitchen.searchById(kitchenId);

//        return ResponseEntity.status(HttpStatus.OK).body(kitchen);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "http://localhost:8080/kitchens");

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .headers(headers)
                .build();
    }
}
