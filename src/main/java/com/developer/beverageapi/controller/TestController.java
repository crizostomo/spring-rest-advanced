package com.developer.beverageapi.controller;

import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.repository.RepositoryKitchen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RepositoryKitchen repositoryKitchen;

    /**
     * @RequestParam is used when you want to search by something typing directly on the query
     * you can put the ("name") after just to be clearer, however you don't need to type it
     * An example: https://localhost:8080/test/kitchens/by-name?name=Brazilian
     */

    @GetMapping("/kitchens/by-name")
    public List<Kitchen> kitchenByName(@RequestParam String name){
        return repositoryKitchen.searchByName(name);
    }
}
