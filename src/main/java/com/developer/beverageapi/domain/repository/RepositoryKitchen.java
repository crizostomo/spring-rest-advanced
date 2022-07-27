package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.Kitchen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryKitchen extends JpaRepository<Kitchen, Long> {

//    List<Kitchen> searchByName(String name);
}
