package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.Restaurant;

import java.util.List;

public interface RepositoryRestaurant {

    List<Restaurant> listAll();
    Restaurant searchById(Long id);
    Restaurant add(Restaurant restaurant);
    void remove(Long id);
}
