package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositoryState extends JpaRepository<State, Long> {

}
