package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.City;

import java.util.List;

public interface RepositoryCity {

    List<City> listAll();
    City searchById(Long id);
    City add(City city);
    void remove(Long id);
}
