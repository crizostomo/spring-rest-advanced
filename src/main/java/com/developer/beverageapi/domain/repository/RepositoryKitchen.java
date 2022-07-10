package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.Kitchen;

import java.util.List;

public interface RepositoryKitchen {

    List<Kitchen> listAll();
    Kitchen searchById(Long id);
    Kitchen add(Kitchen kitchen);
    void remove(Kitchen kitchen);
}
