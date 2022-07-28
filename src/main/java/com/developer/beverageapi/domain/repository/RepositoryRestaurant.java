package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryRestaurant extends JpaRepository<Restaurant, Long> {

}
