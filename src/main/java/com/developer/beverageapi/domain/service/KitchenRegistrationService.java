package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.repository.RepositoryKitchen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KitchenRegistrationService {

    @Autowired
    RepositoryKitchen repositoryKitchen;

    public Kitchen add(Kitchen kitchen){
        return repositoryKitchen.add(kitchen);
    }

    /**
     *     List<City> listAll();
     *     City searchById(Long id);
     *     City add(City city);
     *     void remove(City city);
     */
}
