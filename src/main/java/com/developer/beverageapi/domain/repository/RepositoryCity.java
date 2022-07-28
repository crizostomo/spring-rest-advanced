package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryCity extends JpaRepository<City, Long> {

}
