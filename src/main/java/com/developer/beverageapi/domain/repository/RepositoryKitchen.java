package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.Kitchen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositoryKitchen extends JpaRepository<Kitchen, Long> {

    List<Kitchen> findByName(String name);

    Optional<Kitchen> findOneByName(String name);
}
